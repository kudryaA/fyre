package tss.g2.fyre.models.datastorage.postgress.utils.authorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tss.g2.fyre.models.entity.Person;

/**
 * CLass for get authorization from postgres.
 *
 * @author Anton Kudryavtsev
 */
public class GetAuthorization {
  private static Logger logger = LoggerFactory.getLogger(GetAuthorization.class);
  private Connection connection;
  private String login;

  /**
   * Constructor.
   *
   * @param connection postgres jdbc connection
   * @param login for authorization
   */
  public GetAuthorization(Connection connection, String login) {
    this.connection = connection;
    this.login = login;
  }

  /**
   * Method for get authorization info.
   *
   * @return authorization
   */
  public Map<String, Object> getAuthorization() {
    Person result = null;
    int count = 0;
    try (PreparedStatement statement =
                 connection.prepareStatement("SELECT password, name, surname, email, role, bannedstatus, "
                    + "(select count(login) from mailconfirmation where login = person.login) as count"
                    + " FROM person WHERE login = ?")) {
      statement.setString(1, login);
      logger.info(statement.toString());
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          String password = resultSet.getString("password");
          String name = resultSet.getString("name");
          String surname = resultSet.getString("surname");
          boolean bannedStatus = resultSet.getBoolean("bannedStatus");
          String email = resultSet.getString("email");
          String role = resultSet.getString("role");
          count = resultSet.getInt("count");
          result = new Person(login, password, name, surname, bannedStatus, email, role);
        }
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }
    Map<String, Object> map = new HashMap<>();
    map.put("authorization", result);
    map.put("count", count);
    return map;
  }
}
