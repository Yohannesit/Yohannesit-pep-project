package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message createMessage(Message message) {
        if (message == null || message.getMessage_text() == null || message.getMessage_text().isBlank() || 
            message.getMessage_text().length() > 255) {
            return null;
        }
        if (accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    public Message updateMessage(int messageId, String messageText) {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            return null;
        }
        if (messageDAO.getMessageById(messageId) == null) {
            return null;
        }
        return messageDAO.updateMessage(messageId, messageText);
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}