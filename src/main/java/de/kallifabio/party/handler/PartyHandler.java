package de.kallifabio.party.handler;

import de.kallifabio.party.manager.PartyManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class PartyHandler {

    private static List<PartyManager> parties = new ArrayList();

    public List<PartyManager> getAllParties() {
        return parties;
    }

    public void create(ProxiedPlayer p) {
        if (getParty(p) == null) {
            parties.add(new PartyManager(p));

            p.sendMessage(new ComponentBuilder("§aDu hast erfolgreich eine Party erstellt!").create());
            p.sendMessage(new ComponentBuilder("§eBenutze §6/party invite <player> §ezum einladen eines Spielers").create());
        } else {
            p.sendMessage(new ComponentBuilder("§cDu bist bereits in einer Party!").create());
        }
    }

    public PartyManager getParty(ProxiedPlayer p) {
        for (PartyManager party : parties) {
            if (party.hasParty(p)) {
                return party;
            }
        }
        return null;
    }

    public void disband(ProxiedPlayer p) {
        if (getParty(p) != null) {
            PartyManager party = getParty(p);
            if (party.getOwner() == p) {
                p.sendMessage(new ComponentBuilder("§aDu hast deine Party erfolgreich geschlossen!").create());
                for (ProxiedPlayer members : party.getMembers()) {
                    p.sendMessage(new ComponentBuilder("§c§lDeine derzeitige Party wurde geschlossen!").create());
                    members.sendMessage(new ComponentBuilder("§c§lDeine derzeitige Party wurde geschlossen!").create());

                    party.leave(members);
                }
                parties.remove(getParty(p));
            } else {
                p.sendMessage(new ComponentBuilder("§cDu bist nicht der Owner dieser Party").create());
            }
        }
    }
}
