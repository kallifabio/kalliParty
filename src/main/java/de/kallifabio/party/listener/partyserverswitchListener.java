package de.kallifabio.party.listener;

import de.kallifabio.party.handler.PartyHandler;
import de.kallifabio.party.manager.PartyManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class partyserverswitchListener implements Listener {

    @EventHandler
    public void on(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();

        PartyHandler partyHandler = new PartyHandler();
        if (partyHandler.getParty(p) != null) {
            PartyManager party = partyHandler.getParty(p);
            if (party.getOwner() == p) {
                for (ProxiedPlayer members : party.getMembers()) {
                    if (members.getServer() != p.getServer()) {
                        members.sendMessage(new ComponentBuilder("§eVerbinden zu §a" + p.getServer().getInfo().getName()).create());
                        members.connect(p.getServer().getInfo());
                    }
                }
            }
        }
    }
}
