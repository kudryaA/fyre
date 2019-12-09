package tss.g2.fyre.models.datastorage.postgress.utils.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for confirmation recipe.
 */
public class RecipeConfirmation {
  private static Logger logger = LoggerFactory.getLogger(RecipeConfirmation.class);
  private Connection connection;
  private String recipeId;

  /**
   * Constructor.
   * @param connection connection to database.
   * @param recipeId recipe id
   */
  public RecipeConfirmation(Connection connection, String recipeId) {
    this.connection = connection;
    this.recipeId = recipeId;
  }

  /**
   * Method for edit recipe confirmed status.
   * @return editing result
   */
  public boolean confirmation() {
    boolean result = false;

    try (PreparedStatement confirmationStatement = connection
            .prepareStatement("update recipe set isconfirmed = true where recipe_id = ?")) {
      confirmationStatement.setString(1, recipeId);
      logger.info(confirmationStatement.toString());


      result = confirmationStatement.executeUpdate() == 1;
      try (PreparedStatement updateStatement = connection
              .prepareStatement("UPDATE users_rating SET rating = rating + 10 WHERE user_login  =  " +
                      "(select creator from recipe where recipe_id = ?)")) {
        updateStatement.setString(1, recipeId);

        logger.info(updateStatement.toString());
        updateStatement.executeUpdate();
      }

    } catch (SQLException e) {
      logger.error(e.getMessage());
    }

    return result;
  }
}
