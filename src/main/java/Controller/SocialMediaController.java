package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/:message_id", this::getMessageByIdHandler);
        app.delete("/messages/:message_id", this::deleteMessageHandler);
        app.patch("/messages/:message_id", this::updateMessageHandler);
        app.get("/accounts/:account_id/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    //private void exampleHandler(Context context) {
        //context.json("sample text");
    //}

    private void registerHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account result = accountService.registerAccount(account);
        if (result!=null) {
            context.json(result);
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account result = accountService.loginAccount(account);
        if (result!=null) {
            context.json(result);
        } else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message result = messageService.createMessage(message);
        if (result != null) {
            context.json(result);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(message);
        }
    }

    private void deleteMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessage(messageId);
        if (deleted != null) {
            context.json(deleted);
        }
    }

    private void updateMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message input = context.bodyAsClass(Message.class);
        Message updated = messageService.updateMessage(messageId, input.getMessage_text());
        if (updated != null) {
            context.json(updated);
        } else {
            context.status(400);
        }
    }

    private void getMessagesByAccountHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        context.json(messages);
    }
}