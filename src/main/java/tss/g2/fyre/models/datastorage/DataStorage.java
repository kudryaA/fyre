package tss.g2.fyre.models.datastorage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tss.g2.fyre.models.entity.Comment;
import tss.g2.fyre.models.entity.Person;
import tss.g2.fyre.models.entity.Type;
import tss.g2.fyre.models.entity.UserRating;
import tss.g2.fyre.models.entity.recipe.Recipe;
import tss.g2.fyre.models.entity.recipe.RecipeWithType;

/**
 * This interface describe data storage worker.
 *
 * @author Anton Kudryavtsev
 */
public interface DataStorage {
  
  /**
   * Get authorization info by login.
   *
   * @param login login
   * @return authorization info
   */
  Person getAuthorization(String login);

  /**
   * Method for adding new person.
   *
   * @param login user login
   * @param password user password
   * @param name user name
   * @param surname user surname
   * @param email user email
   * @return result of adding user
   */
  boolean createUser(String login, String password, String name, String surname, String email);

  /**
   * Method for adding new recipe.
   *
   * @param recipeName recipe name
   * @param recipeComposition composition of the recipe
   * @param cookingSteps recipe cooking steps
   * @param publicationDate recipe publication date
   * @param selectedTypes list with types that the moderator selects
   * @param image image of recipe
   * @param user owner of recipe
   * @param isConfirmed is the recipe confirmed
   * @return id of recipe
   */
  String addRecipe(String recipeName, String recipeComposition, String cookingSteps,
                    Date publicationDate, List<String> selectedTypes, String image,
                    String user, boolean isConfirmed);

  /**
   * Method for adding new type.
   *
   * @param typeName name of type
   * @param description type description
   * @param image path to image
   * @return result of adding type
   */
  boolean addType(String typeName, String description, String image);

  /**
   * Method for select persons information.
   *
   * @return list with persons info
   */
  List<Person> getPersonsInformation();

  /**
   * Method for select users rating information.
   *
   * @return list with users info
   */
  List<UserRating> selectUsersRating();

  /**
   * Method for select types information.
   *
   * @return list with types info
   */
  List<Type> getTypesInformation();

  /**
   * Method for change user banned status.
   * @param userLogin user login
   * @return result of changing status
   */
  boolean changeBannedStatus(String userLogin);

  /**
   * Method for removing the recipe.
   * @param recipeId recipe id
   * @param user authorization user
   * @return result of deleting
   */
  boolean deleteRecipe(String recipeId, String user);

  /**
   * Get recipe by id.
   * @param recipeId id of recipe
   * @return some recipe
   */
  Recipe getRecipe(String recipeId);

  /**
   * Method for get the requested information.
   *
   * @param pageNumber page number
   * @param pageSize page size
   * @param recipeType recipe type
   * @param sortType sort type
   * @return the requested information
   */
  Map<String, Object> selectRecipes(int pageNumber, int pageSize,
                                    String recipeType, String sortType);

  /**
   * Method for update recipe.
   * @param recipeId recipe id
   * @param recipeName recipe name
   * @param composition composition
   * @param cookingSteps cooking steps
   * @param creator user who create recipe
   * @return the result of the update
   */
  boolean updateRecipe(String recipeId, String recipeName, String composition,
                       String cookingSteps, String creator);

  /**
   * Method for search recipe by ingredient name.
   *
   * @param ingredientName ingredient name
   * @param pageNumber page number
   * @param pageSize page size
   * @return the recipe found
   */
  Map<String, Object> searchRecipe(String ingredientName, int pageNumber, int pageSize);

  /**
   * Method for get user role.
   * @param login user login
   * @return user role
   */
  String getRole(String login);

  /**
   * Method for get unconfirmed recipes.
   */
  List<RecipeWithType> selectUnconfirmedRecipes();

  /**
   * Method for edit recipe confirmed status.
   * @param recipeId recipe id
   * @return edition result
   */
  boolean recipeConfirmation(String recipeId);

  /**
   * Method for add comment to recipe.
   * @param userLogin user login
   * @param recipeId recipe id
   * @param commentText comment text
   * @return result of adding
   */
  boolean addComment(String userLogin, String recipeId, String commentText);

  /**
   * Method for select all comments by recipe id.
   * @param recipeId recipe id
   * @return comments list
   */
  List<Comment> selectComments(String recipeId);

  /**
   * Method for add like to recipe.
   * @param login user login
   * @param recipeId recipe id
   * @return result of add
   */
  boolean addLike(String login, String recipeId);

  /**
   * Method for checking like on a recipe from a given user.
   * @param login user login
   * @param recipeId recipe id
   * @return result of check
   */
  boolean checkLike(String login, String recipeId);

  /**
   * Store time api.
   * @param api api
   * @param time time in milliseconds
   */
  boolean addTimeApi(String api, long time);
}
