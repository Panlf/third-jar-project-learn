package com.plf.learn.calcite;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

/**
 * Apache Calcite 是一款开源SQL解析工具，可以将各种SQL语句解析成
 * 抽象语法树AST(Abstract Syntax Tree)
 *
 * SQL解析
 * SQL校验
 * 查询优化
 * SQL生成器
 * 数据连接
 *
 * @author panlf
 * @date 2021/10/25
 */
public class CalciteTest {
    public static void main(String[] args) {
        SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL);
        SqlParser sqlParser = SqlParser.create("select id,name from test",config);
        try {
            SqlNode sqlNode = sqlParser.parseQuery();
            SqlSelect sqlSelect = (SqlSelect) sqlNode;
            System.out.println(sqlSelect.getFrom()+"--"+sqlSelect.getSelectList().getList());
        } catch (SqlParseException e) {
            e.printStackTrace();
        }

    }
}
