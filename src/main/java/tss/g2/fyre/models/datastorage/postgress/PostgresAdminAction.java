package tss.g2.fyre.models.datastorage.postgress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for handling administrator actions.
 * @author Andrey Sherstyuk
 */
public class PostgresAdminAction {
  private Connection connection;
  private String userLogin;

  /**
   * Constructor.
   *
   * @param connection connection to database
   * @param userLogin user login
   */
  public PostgresAdminAction(Connection connection, String userLogin) {
    this.connection = connection;
    this.userLogin = userLogin;
  }

  /**
   * Method for change user banned status.
   * @return result of changes
   */
  public boolean changeBannedStatus() {
    boolean result = false;

    try (PreparedStatement changeStatusStatement =
                 connection.prepareStatement("update person set bannedstatus = "
                 + "case when (select bannedstatus from person where login = ?) = false then true "
                 + "else false end where login = ?")) {
      changeStatusStatement.setString(1, userLogin);
      changeStatusStatement.setString(2, userLogin);

      result = changeStatusStatement.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
