package com.marine.game;

import com.marine.StandaloneServer;
import com.marine.game.async.ChatManager;
import com.marine.game.async.TimeoutManager;
import com.marine.net.Client;
import com.marine.net.Packet;
import com.marine.net.States;
import com.marine.net.play.clientbound.JoinGamePacket;
import com.marine.net.play.clientbound.KickPacket;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.Player;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final StandaloneServer server;
    private Set<Player> allPlayers;
    // private Map<UUID, Player> playerIDs;
    private Map<Short, Player> uids;
    private short uid = -1;
    private Map<String, Player> playerNames;
    private LoginHandler loginManager;
    private TimeoutManager timeout;

    private MovmentManager movment;
    private ChatManager chat;

    public PlayerManager(StandaloneServer server) {
        this.server = server;
        loginManager = new LoginHandler(this, this.server.getWorldManager().getMainWorld(), this.server.getWorldManager().getMainWorld().getSpawnPoint());
        allPlayers = Collections.synchronizedSet(new HashSet<Player>());
        // playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
        uids = Collections.synchronizedMap(new ConcurrentHashMap<Short, Player>());
        playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());

        timeout = new TimeoutManager(this);
        chat = new ChatManager(this);
        movment = new MovmentManager(this);

        timeout.start();
    }

    public ChatManager getChat() {
        return chat;
    }

    public void broadcastPacket(Packet packet) {
        for (Player p : allPlayers)
            p.getClient().sendPacket(packet);
    }

    public void updateThemAll() {
        for (Player p : allPlayers) {
            p.update();
            p.sendTime();
        }
    }

    public short getNextUID() {
        return ++uid;
    }

    public StandaloneServer getServer() {
        return server;
    }

    public boolean isPlayerOnline(String name) {
        return playerNames.containsKey(name);
    }

    public boolean isPlayerOnline(UUID uid) {
        for(Player player : uids.values())
            if(player.getUUID().equals(uid))
                return true;
        return false;
    }

    public boolean isPlayerOnline(short uid) {
        return uids.containsKey(uid);
    }

    protected void putPlayer(Player p) {
        if (allPlayers.contains(p))
            return;

        allPlayers.add(p);
        //playerIDs.put(p.getUUID(), p);
        uids.put(p.getUID(), p);
        playerNames.put(p.getName(), p);
    }

    public Player getPlayer(UUID uuid) {
        // if (!playerIDs.containsKey(uuid))
        //    return null;
        // return playerIDs.get(uuid);
        for(Player player : uids.values())
            if(player.getUUID().equals(uuid))
                return player;
        return null;
    }

    public Player getPlayer(short uid) {
        if(uids.containsKey(uid))
            return uids.get(uid);
        return null;
    }

    public Player getPlayer(String displayName) {
        if (!playerNames.containsKey(displayName))
            return null;
        return playerNames.get(displayName);

    }

    protected void removePlayer(Player p) {
        synchronized (allPlayers) {
            synchronized (uids) {
                synchronized (playerNames) {
                    if (allPlayers.contains(p)) {
                        allPlayers.remove(p);
                        uids.remove(p.getUID());
                        playerNames.remove(p.getName());
                    }
                }
            }
        } // Sync end
    }

    public void tickAllPlayers() {
        synchronized (allPlayers) {
            for (IPlayer p : allPlayers)
                if (p instanceof Player) {
                    Player pl = (Player) p;
                    pl.tick();
                }
        }
    }

    public LoginHandler getLoginManager() {
        return loginManager;
    }

    public Set<Player> getPlayers() {
        return allPlayers;
    }

    protected Player passFromLogin(IPlayer player) {
        if (player instanceof Player) {
            putPlayer((Player) player);
            return (Player) player;
        } else if (player instanceof AbstractPlayer) {
            Player p = new Player((AbstractPlayer) player, server.getGamemode());
            putPlayer(p);
            return p;
        }
        return null; // This shoulnt happening if id does its wierd :S
    }

    public Player getPlayerByClient(Client c) {
        for (Player player : getPlayers()) {
            if (player.getClient() == c)
                return player;
        }
        return null;
    }

    private void cleanUp(Player p) {
        removePlayer(p);
        timeout.cleanUp(p);
        //TODO: send player remove packet to every other client
        server.getNetwork().cleanUp(p.getClient());
        forceGC(p);
    }

    public void disconnect(Player p, String msg) {
        if (p == null)
            return;
        p.disconnect();
        p.getClient().sendPacket(new KickPacket(msg));
        cleanUp(p);
    }

    public void joinGame(Player p) {
        if (p.getClient().getState() != States.INGAME) {
            cleanUp(p);
            return;
        }

        timeout.addPlayerToManager(p);

        p.getClient().sendPacket(new JoinGamePacket(p));

        p.sendPosition();

        p.sendMapData(p.getWorld().getChunks(0, 0, 7, 7));

        p.sendAbilites();

        p.sendPosition();
        p.sendTime();
        //p.loginPopulation();
    }

    public void keepAlive(short uid, int ID) {
        if(uid == -1)
            return;
        timeout.keepAlive(getPlayer(uid), ID);
    }

    public void forceGC(Player player) {
        WeakReference ref = new WeakReference<>(player);
        player = null;
        while (ref.get() != null) {
            System.gc();
        }
    }

    public MovmentManager getMovmentManager() {
        return movment;
    }

    public boolean hasAnyPlayers() {
        return !allPlayers.isEmpty();
    }
}
