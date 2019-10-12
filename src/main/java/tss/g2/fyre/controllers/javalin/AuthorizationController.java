package tss.g2.fyre.controllers.javalin;

import io.javalin.Javalin;
import tss.g2.fyre.controllers.CreateController;
import tss.g2.fyre.models.Answer;
import tss.g2.fyre.models.actions.CheckAuthorization;
import tss.g2.fyre.models.actions.CheckModerator;
import tss.g2.fyre.models.actions.RegisterModerator;
import tss.g2.fyre.models.actions.RegisterUser;
import tss.g2.fyre.models.datastorage.DataStorage;

/**
 * Person controller for javalin.
 * @author Anton Kudryavtsev
 */
public class AuthorizationController implements CreateController {

  private Javalin app;
  private DataStorage dataStorage;

  /**
   * Constructor.
   * @param app javalin app
   * @param dataStorage storage of data
   */
  public AuthorizationController(Javalin app, DataStorage dataStorage) {
    this.app = app;
    this.dataStorage = dataStorage;
  }

  @Override
  public void create() {
    app.post("/login", ctx -> {
      String login = ctx.queryParam("login");
      String password = ctx.queryParam("password");
      Answer answer = new CheckAuthorization(dataStorage, login, password).getAnswer();
      ctx.result(answer.toJson());
    });

    app.post("/registration", ctx -> {
      String login = ctx.queryParam("login");
      String password = ctx.queryParam("password");
      String name = ctx.queryParam("name");
      String surname = ctx.queryParam("surname");
      String email = ctx.queryParam("email");

      Answer answer = new RegisterUser(dataStorage, login, password, name, surname, email)
              .getAnswer();
      ctx.result(answer.toJson());
    });
    
    app.post("/login/moderator", ctx -> {
      String login = ctx.queryParam("login");
      String password = ctx.queryParam("password");
      Answer answer = new CheckModerator(dataStorage, login, password).getAnswer();
      ctx.result(answer.toJson());
    });

    app.post("/registration/moderator", ctx -> {
      String name = ctx.queryParam("name");
      String login = ctx.queryParam("login");
      String password = ctx.queryParam("password");

      Answer answer = new RegisterModerator(dataStorage, login, password, name)
              .getAnswer();
      ctx.result(answer.toJson());
    });
  }
}
