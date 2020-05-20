package fudan.pbl.mm.controller.request;

import fudan.pbl.mm.domain.Pack;
import fudan.pbl.mm.domain.Position;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.domain.Virus;

import java.util.Map;
import java.util.Set;

public class StartGameResponse {
    private Pack pack;
    private Map<User, Position> cellPositionMap;
    private Map<Virus, Position> virusPositionMap;
    private User newUser;

    public StartGameResponse(){}
    public StartGameResponse(Pack pack, Map<User, Position> cellPositionMap,
            Map<Virus, Position> virusPositionMap, User newUser){
        this.pack = pack;
        this.cellPositionMap = cellPositionMap;
        this.virusPositionMap = virusPositionMap;
        this.newUser = newUser;
    }

    public Map<User, Position> getCellPositionMap() {
        return cellPositionMap;
    }

    public Map<Virus, Position> getVirusPositionMap() {
        return virusPositionMap;
    }

    public void setCellPositionMap(Map<User, Position> cellPositionMap) {
        this.cellPositionMap = cellPositionMap;
    }

    public void setVirusPositionMap(Map<Virus, Position> virusPositionMap) {
        this.virusPositionMap = virusPositionMap;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Pack getPack() {
        return pack;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}
