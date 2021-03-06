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

package org.marinemc.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.marinemc.gui.ConsoleWindow;
import org.marinemc.plugins.PluginLogger;
import org.marinemc.util.StringUtils;

/**
 * Logging class - Used for all logging purposes, even the built in plugin
 * loggers.
 *
 * @author Fozie
 * @author Citymonstret
 */
public class Logging extends PrintStream {

	private static Logging instance;
	private final Calendar calendar = GregorianCalendar.getInstance();
	private final List<String> list;
	private final ConsoleWindow c;
	private boolean haveWindow;

	public Logging(final ConsoleWindow window) {
		super(window);
		list = new ArrayList<>();
		haveWindow = false;
		c = window;
	}

	public static Logging getLogger() {
		if (instance == null) {
			final ConsoleWindow window = new ConsoleWindow(25);
			instance = new Logging(window);
		}
		return instance;
	}

	public static Logging instance() {
		return getLogger();
	}

	public void createConsoleWindow() {
		c.initWindow();
		haveWindow = true;
	}

	public void clearLogger() {
		c.clear();
	}

	public boolean isDisplayed() {
		return haveWindow;
	}

	public boolean hasBeenTerminated() {
		return c.isClosed();
	}

	public void log(final PluginLogger.PluginMessage message) {
		log(message.getMessage());
	}

	public void logf(final String s, final Object... os) {
		this.log(StringUtils.format(s, os));
	}

	public void log(final String s) {
		c.write(format('3', s).replace("<", "&lt;").replace(">", "&gt;"));
		final String l = format(s);
		System.out.println(l);
		list.add(l);
	}

	public void info(final String s) {
		c.write(format('9', "INFO", s));
		final String l = format("INFO", s);
		System.out.println(l);
		list.add(l);
	}

	public void debug(final String s) {
		c.write(format('2', "DEBUG", s));
		final String l = format("DEBUG", s);
		System.out.println(l);
		list.add(l);
	}

	public void fatal(final String s) {
		c.write(format('c', "FATAL", s));
		final String l = format("FATAL", s);
		System.out.println(l);
		list.add(l);
	}

	public void error(final String s, final Throwable cause) {
		c.write(format('c', "ERROR", s));
		final String l = format("ERROR", s);
		System.out.println(l);
		list.add(l);
		// We should handle this better
		// TODO Better solution
		cause.printStackTrace();
	}

	public void error(final String s) {
		c.write(format('c', "ERROR", s));
		final String l = format("ERROR", s);
		System.out.println(l);
		list.add(l);
	}

	public void warn(final String s) {
		c.write(format('c', "WARNING", s));
		final String l = format("WARNING", s);
		System.out.println(l);
		list.add(l);
	}

	public void warn(final String s, final Throwable cause) {
		c.write(format('c', "WARNING", s));
		final String l = format("WARNING", s);
		System.out.println(l);
		list.add(l);
		cause.printStackTrace();
	}

	private String format(final char color, final String prefix,
			final String msg) {
		final Date date = new Date();
		calendar.setTime(date);
		return String.format("§%c[%s:%s:%s] [%s] §0%s", color,
				get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE),
				get(Calendar.SECOND), prefix, msg);
	}

	private String get(final int n) {
		final int l = calendar.get(n);
		return l < 10 ? "0" + l : l + "";
	}

	private String format(final String prefix, final String msg) {
		final Date date = new Date();
		calendar.setTime(date);
		return String.format("[%s:%s:%s] [%s] %s", get(Calendar.HOUR_OF_DAY),
				get(Calendar.MINUTE), get(Calendar.SECOND), prefix, msg)
				.replaceAll("§([a-z0-9])", "");
	}

	private String format(final String msg) {
		final Date date = new Date();
		calendar.setTime(date);
		return String.format("[%s:%s:%s] %s", get(Calendar.HOUR_OF_DAY),
				get(Calendar.MINUTE), get(Calendar.SECOND), msg).replaceAll(
				"§([a-z0-9])", "");
	}

	private String format(final char color, final String msg) {
		final Date date = new Date();
		calendar.setTime(date);
		return String.format("§%c§l[%s:%s:%s] §0%s", color,
				get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE),
				get(Calendar.SECOND), msg);
	}

	public void saveLog() {
		File file;
		final File parent = new File("./log");
		if (!parent.exists() && !parent.mkdir())
			throw new RuntimeException("Could not create the log parent folder");
		try {
			compress(new File(parent, "old.zip"),
					parent.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(final File dir, final String name) {
							return name.endsWith(".log");
						}
					}));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		file = new File(parent, calendar.get(Calendar.YEAR) + "-"
				+ calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.WEEK_OF_MONTH) + "-"
				+ calendar.get(Calendar.DAY_OF_WEEK) + "-"
				+ calendar.get(Calendar.HOUR_OF_DAY) + "_"
				+ calendar.get(Calendar.MINUTE) + "_"
				+ calendar.get(Calendar.SECOND) + ".log");
		if (file.exists())
			file.delete();
		try {
			if (!file.createNewFile())
				throw new RuntimeException("Could not create log");
			final PrintWriter writer = new PrintWriter(file);
			for (final String line : list)
				writer.println(line);
			writer.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void compress(final File zipFile, final File[] files)
			throws IOException {
		if (!zipFile.exists())
			if (!zipFile.createNewFile())
				throw new RuntimeException("Could not create"
						+ zipFile.getPath());
		final File tempFile = File.createTempFile(zipFile.getName(), null);
		tempFile.delete();
		if (!zipFile.renameTo(tempFile))
			throw new RuntimeException("Could not rename the file "
					+ zipFile.getAbsolutePath() + " to "
					+ tempFile.getAbsolutePath());
		final byte[] buf = new byte[1024 * 1024]; // 1mb
		final ZipInputStream zin = new ZipInputStream(new FileInputStream(
				tempFile));
		final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFile));
		ZipEntry entry = zin.getNextEntry();
		while (entry != null) {
			final String name = entry.getName();
			boolean notInFiles = true;
			for (final File f : files)
				if (f.getName().equals(name)) {
					notInFiles = false;
					break;
				}
			if (notInFiles) {
				out.putNextEntry(new ZipEntry(name));
				int len;
				while ((len = zin.read(buf)) > 0)
					out.write(buf, 0, len);
			}
			entry = zin.getNextEntry();
		}
		zin.close();
		for (final File file1 : files) {
			final InputStream in = new FileInputStream(file1);
			out.putNextEntry(new ZipEntry(file1.getName()));
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			out.closeEntry();
			in.close();
		}
		for (final File file : files)
			file.delete();
		out.close();
		tempFile.delete();
	}

}
