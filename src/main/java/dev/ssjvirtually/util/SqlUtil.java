package dev.ssjvirtually.util;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

public final class SqlUtil {

    private SqlUtil() {
    }

    public static String format(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return "";
        }
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            return statement.toString();
        } catch (Exception e) {
            // If it fails to parse, return original or a message
            throw new IllegalArgumentException("Invalid SQL: " + e.getMessage());
        }
    }
}
