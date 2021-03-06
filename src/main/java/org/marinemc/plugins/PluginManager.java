///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.plugins;

import java.util.*;

/**
 * Plugin Manager
 * <p/>
 * This should be used rather than the @Protected PluginLoader
 *
 * @author Citymonstret
 */
public class PluginManager {

	private final Map<String, Plugin> plugins;

	/**
	 * Constructor
	 */
	public PluginManager() {
		plugins = new HashMap<>();
	}

	/**
	 * Add a plugin to the plugin list, you have to do this before enabling the
	 * plugin.
	 *
	 * @param plugin
	 *            Plugin to add
	 */
	public void addPlugin(final Plugin plugin) {
		plugins.put(plugin.getProviderName(), plugin);
	}

	/**
	 * Remove a plugin from the list
	 *
	 * @param plugin
	 *            Plugin to remove
	 */
	public void removePlugin(final Plugin plugin) {
		if (plugins.containsKey(plugin.getProviderName()))
			plugins.remove(plugin.getProviderName());
	}

	public Plugin getPlugin(final String providerName) {
		return this.plugins.get(providerName);
	}

	/**
	 * Enable a plugin
	 *
	 * @param plugin
	 *            Plugin to enable
	 * @throws java.lang.RuntimeException
	 *             if the plugin is not added to the plugin list {@see
	 *             #addPlugin(com.marine.Plugin)}
	 */
	protected void enablePlugin(final Plugin plugin) {
		if (!plugins.containsKey(plugin.getProviderName()))
			throw new RuntimeException("Plugin: " + plugin.getName()
					+ " is not added to the plugin list, can't enable");
		plugin.enable();
	}

	/**
	 * Disable a plugin
	 *
	 * @param plugin
	 *            Plugin to disable
	 * @throws java.lang.RuntimeException
	 *             if the plugin is not added to the plugin list {@see
	 *             #addPlugin(com.marine.Plugin)}
	 */
	protected void disablePlugin(final Plugin plugin) {
		if (!plugins.containsKey(plugin.getProviderName()))
			throw new RuntimeException("Plugin: " + plugin.getName()
					+ " is not added to the plugin list, can't disable");
		plugin.disable();
	}

	/**
	 * Get a collection containing ALL plugins
	 *
	 * @return all plugins
	 */
	public Collection<Plugin> getPlugins() {
		return plugins.values();
	}

	/**
	 * Get the plugins in strng format
	 *
	 * @return A collection containing the names of the plugins
	 */
	public Collection<String> getPluginNames() {
		final List<String> strings = new ArrayList<>();
		for (final Plugin plugin : plugins.values())
			strings.add(plugin.getName());
		return strings;
	}

	/**
	 * Get a collection containing all ENABLED plugins
	 *
	 * @return all enabled plugins
	 */
	public Collection<Plugin> getEnabledPlugins() {
		final Collection<Plugin> plugins = new ArrayList<>();
		for (final Plugin plugin : this.plugins.values())
			if (plugin.isEnabled())
				plugins.add(plugin);
		return plugins;
	}
}
