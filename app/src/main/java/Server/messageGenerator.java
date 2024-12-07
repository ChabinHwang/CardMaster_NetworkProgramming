package Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class messageGenerator {
    JSONObject response;
    JSONObject data;
    public messageGenerator(){}

    public JSONObject loginSuccess(gameList games,User user){
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
        data.put("numberOfGame",games.numberOfGames())
                .put("money",user.getMoney());
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
        response.put("response", "blackJackCard");
        data.put("playerTurn", playerTurn);
        data.put("currentBet", currentBet);
        response.put("data", data);
        return response;
    }

    public JSONObject blackJackCard(List<Card> playerHands, List<Card> dealerCards) {
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

        // Finalize response
        response.put("response", "blackJackCard");
        response.put("data", data);

        return response;
    }
    public JSONObject updateHand(List<Card> playerHands){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "updateHand");
        data.put("playerCard", playerHands);
        response.put("data", data);
        return response;
    }
    public JSONObject casinoWarCard(List<Card> playerHand){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "casinoWarCard");
        data.put("playerCard", playerHand);
        response.put("data", data);
        return response;
    }
    public JSONObject gameResult(int amount, String result, List<Card> playerCards, List<Card> dealerCards){
        response = new JSONObject();
        data = new JSONObject();
        response.put("response", "gameResult");
        data.put("result", result)
                .put("playerCards", playerCards)
                .put("dealerCards", dealerCards)
                .put("prize", amount);
        response.put("data", data);
        return response;
    }
}
