// Copyright 2021 Sebastian Kuerten
//
// This file is part of sqlite-utils.
//
// sqlite-utils is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// sqlite-utils is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with sqlite-utils. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.sqliteutils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.processutils.ProcessLogger;

public class SqliteUtil
{

	static final Logger logger = LoggerFactory.getLogger(SqliteUtil.class);

	private static final String prefix = "jdbc:sqlite:";

	public static void vacuum(File databaseFile) throws IOException
	{
		logger.info("Executing VACUUM");
		Process process = Runtime.getRuntime().exec(
				new String[] { "sqlite3", databaseFile.toString(), "vacuum" });
		ProcessLogger processLogger = new ProcessLogger(process, logger);
		processLogger.waitForEnd();
	}

	public static void analyze(File databaseFile) throws SQLException
	{
		logger.info("Executing ANALYZE");
		Connection connection = DriverManager.getConnection(prefix
				+ databaseFile.toString());
		analyze(connection);
		connection.close();
	}

	public static void analyze(Connection connection) throws SQLException
	{
		logger.info("Executing ANALYZE");
		connection.createStatement().executeUpdate("analyze");
	}

}
