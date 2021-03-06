package bowling.dao;

import bowling.entity.BaseEntity;

import java.sql.*;
import java.util.Calendar;

/**
 * データベースアクセスのスーパークラス
 */
abstract public class BaseDao {

    private static final String USER = "study1";
    private static final String PASSWORD = "devStudy1!";
    private static final String URL = "jdbc:mysql://localhost:3306/javaedu";

    private Connection con;

    protected BaseDao(Connection con) throws SQLException {
        this.con = con;
    }

    /**
     * 初期処理を行う
     * @throws SQLException SQL例外
     */
    abstract public void init() throws SQLException;

    /**
     * データベースのコネクションを取得する
     *
     * @return コネクション
     * @throws SQLException SQL例外
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("データベースに接続できませんでした");
            throw e;
        }
    }

    /**
     * 保存時のパラメータをセットします
     * @param entity エンティティ基底クラス
     */
    protected void setSaveParams(BaseEntity entity) {
        Calendar now = Calendar.getInstance();

        entity.setEntryDate(new Timestamp(now.getTimeInMillis()));
        entity.setUpdDate(new Timestamp(now.getTimeInMillis()));
        entity.setVersion(1);
    }

    /**
     * 更新自のパラメータをセットします
     * @param entity エンティティ基底クラス
     */
    protected void setUpdateParams(BaseEntity entity) {

        entity.setUpdDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    }


    /**
     * selectを実行します
     *
     * @param sql sql
     * @return 結果セット
     */
    protected ResultSet executeQuery(String sql) throws SQLException {

        try {
            Statement statement = getCon().createStatement();
            return statement.executeQuery(sql);

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * insert, update, deleteを実行します
     *
     * @param ps プリペアドステートメント
     */
    protected void executeUpdate(PreparedStatement ps) throws SQLException {

        try {
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * selectを実行します
     *
     * @param ps プリペアドステートメント
     * @return 結果セット
     * @throws SQLException SQL例外
     */
    protected ResultSet executeQuery(PreparedStatement ps) throws SQLException {

        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
    }

    protected Connection getCon() {
        return this.con;
    }
}
