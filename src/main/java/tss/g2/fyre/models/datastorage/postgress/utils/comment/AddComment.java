package tss.g2.fyre.models.datastorage.postgress.utils.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for add new comment to recipe.
 */
public class AddComment {
  private static Logger logger = LoggerFactory.getLogger(AddComment.class);
  private Connection connection;
  private String userLogin;
  private String recipeId;
  private String commentText;

  /**
   * Constructor.
   * @param connection connection to database
   * @param userLogin user login
   * @param recipeId recipe id
   * @param commentText comment text
   */
  public AddComment(Connection connection, String userLogin, String recipeId, String commentText) {
    this.connection = connection;
    this.userLogin = userLogin;
    this.recipeId = recipeId;
    this.commentText = commentText;
  }

  /**
   * Method for add new comment to recipe.
   * @return result of adding
   */
  public boolean addComment() {
    boolean result = false;

    try (PreparedStatement addCommentStatement = connection
            .prepareStatement("insert into comment values(?, ?, ?)")) {
      addCommentStatement.setString(1, userLogin);
      addCommentStatement.setString(2, recipeId);
      addCommentStatement.setString(3, commentText);
      logger.info(addCommentStatement.toString());

      result = addCommentStatement.executeUpdate() == 1;
      logger.info("Adding comment complete successfully.");
      try (PreparedStatement checkStatement = connection
              .prepareStatement("select 1 from comment where user_login = ? and recipe_id = ? having count(*) = 1")) {
        checkStatement.setString(1, userLogin);
        checkStatement.setString(2, recipeId);
        try (ResultSet resultSet = checkStatement.executeQuery()) {
          if (resultSet.next()) {
            try (PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE users_rating SET rating = rating + 2 WHERE user_login  = ?")) {
              updateStatement.setString(1, userLogin);

              logger.info(updateStatement.toString());
              updateStatement.executeUpdate();
            }
            try (PreparedStatement newcheckStatement = connection
                    .prepareStatement("select 1 from comment join recipe on (user_login = ? and "
                            + "comment.recipe_id = recipe.recipe_id and user_login != creator and "
                            + "comment.recipe_id = ?)")) {
              newcheckStatement.setString(1, userLogin);
              newcheckStatement.setString(2, recipeId);
              try (ResultSet newResultSet = newcheckStatement.executeQuery()) {
                if (newResultSet.next()) {
                  try (PreparedStatement updateStatement = connection
                          .prepareStatement("UPDATE recipe SET rating = rating + 1 WHERE recipe_id = ?")) {
                    updateStatement.setString(1, recipeId);

                    logger.info(updateStatement.toString());
                    updateStatement.executeUpdate();
                  }
                  try (PreparedStatement updateStatement = connection
                          .prepareStatement("UPDATE users_rating SET rating=rating+1 WHERE user_login  = (SELECT creator from recipe where recipe_id = ? )")) {
                    updateStatement.setString(1, recipeId);

                    logger.info(updateStatement.toString());
                    updateStatement.executeUpdate();
                  }
                }
              }
            }
          }
        }
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }

    return result;
  }
}
