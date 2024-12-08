package Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class messageGenerator {
    JSONObject response;
    JSONObject data;
    public messageGenerator(){}

    public JSONObject loginSuccess(gameList games, User user){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "loginSuccess");
        for(int i=0; i<games.numberOfGames(); i++){
            int tmp = 0;
            for(int j=0; j<games.getGameInstance(i).numberOfRoom(); j++){
                tmp += games.getGameInstance(i).getRoomInstance(j).numberOfPlayer();
            }
            data.put("game"+i, tmp);
        }
        data.put("numberOfGames",games.numberOfGames())
                .put("money",user.getMoney())
                        .put("name", user.getId());
        response.put("data", data);
        return response;
    }
    public JSONObject sendMessage(String message, int gameId){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "message");
        data.put("message", message)
                        .put("gameId", gameId);
        response.put("data", data);
        return response;
    }
    public JSONObject sendRoomList(game game){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "roomList");
        data.put("numberOfRoom", game.numberOfRoom());
        for(int i=0; i<game.numberOfRoom(); i++){
            data.put("numberOfPlayer", new JSONObject()
                    .put("Server.room"+i, game.numberOfPlayer(i)));
        }
        for(int i=0; i<game.numberOfRoom(); i++){
            data.put("roomName", new JSONObject()
                    .put("Server.room"+i, game.getRoomName(i)));
        }
        response.put("data", data);
        return response;
    }
    public JSONObject sendRoomState(room room, int gameId){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "roomState");
        data.put("numberOfPlayer", room.numberOfPlayer())
                .put("roomName", room.getRoomName())
                        .put("gameId", gameId);
        response.put("data", data);
        return response;
    }
    public JSONObject updateRoomState(room room){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "roomStateUpdate");
        data.put("numberOfPlayer", room.numberOfPlayer());
        response.put("data", data);
        return response;
    }
    public JSONObject registerSuccess(){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "registerSuccess");
        data.put("message", "register Success");
        response.put("data", data);
        return response;
    }
    public JSONObject updatePlayerState(boolean state){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "enterSuccess");
        data.put("playing", state);
        response.put("data", data);
        return response;
    }
    public JSONObject errorMessage(String message){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "error");
        data.put("message", message);
        response.put("data", data);
        return response;
    }
    public JSONObject gameUpdate(String playerTurn, int currentBet){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "gameUpdate");
        data.put("playerTurn", playerTurn);
        data.put("currentBet", currentBet);
        response.put("data", data);
        return response;
    }
    public JSONObject updateMoney(int amount){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "moneyUpdate");
        data.put("amount", amount);
        response.put("data", data);
        return response;
    }
    public JSONObject sendDealerPlayerCard(List<Card> playerHands, List<Card> dealerCards, int gameId) {
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        // Convert playerHands to JSON array
        JSONArray playerCardArray = new JSONArray();
        for (Card card : playerHands) {
            JSONObject cardJson = new JSONObject();
            cardJson.put("suit", card.getSuit());
            cardJson.put("rank", card.getRank());
            playerCardArray.put(cardJson);
        }

        // Convert dealer's first card to JSON
        if (dealerCards != null && !dealerCards.isEmpty()) {
            Card dealerFirstCard = dealerCards.get(0); // Assuming getFirst() is get(0)
            JSONObject dealerCardJson = new JSONObject();
            dealerCardJson.put("suit", dealerFirstCard.getSuit());
            dealerCardJson.put("rank", dealerFirstCard.getRank());

            // Add to data object
            data.put("dealerCard", dealerCardJson);
        } else {
            data.put("dealerCard", JSONObject.NULL); // Handle empty dealerCards case
        }

        // Add player cards to data
        data.put("playerCard", playerCardArray);
        data.put("gameId", gameId);

        // Finalize response
        response.put("response", "dealerPlayerCards");
        response.put("data", data);

        return response;
    }
    public JSONObject updateHand(List<Card> playerHands){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "updateHand");
        JSONArray playerCardArray = new JSONArray();
        for (Card card : playerHands) {
            JSONObject cardJson = new JSONObject();
            cardJson.put("suit", card.getSuit());
            cardJson.put("rank", card.getRank());
            playerCardArray.put(cardJson);
        }
        data.put("playerCard", playerCardArray);
        response.put("data", data);
        return response;
    }
    public JSONObject casinoWarCard(List<Card> playerHands){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "casinoWarCard");
        JSONArray playerCardArray = new JSONArray();
        for (Card card : playerHands) {
            JSONObject cardJson = new JSONObject();
            cardJson.put("suit", card.getSuit());
            cardJson.put("rank", card.getRank());
            playerCardArray.put(cardJson);
        }
        data.put("playerCard", playerCardArray);
        response.put("data", data);
        return response;
    }
    public JSONObject gameResult(int amount, String result, List<Card> playerHands, List<Card> dealerCards, int gameId){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "gameResult");
        JSONArray playerCardArray = new JSONArray();
        for (Card card : playerHands) {
            JSONObject cardJson = new JSONObject();
            cardJson.put("suit", card.getSuit());
            cardJson.put("rank", card.getRank());
            playerCardArray.put(cardJson);
        }
        data.put("playerCard", playerCardArray);
        JSONArray dealerCardArray = new JSONArray();
        for (Card card : dealerCards) {
            JSONObject cardJson = new JSONObject();
            cardJson.put("suit", card.getSuit());
            cardJson.put("rank", card.getRank());
            dealerCardArray.put(cardJson);
        }
        data.put("playerCard", playerCardArray);
        data.put("dealerCard", dealerCardArray);
        data.put("gameId", gameId);
        data.put("result", result)
                .put("prize", amount);
        response.put("data", data);
        return response;
    }
}
