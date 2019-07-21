package de.kallifabio.party.listener;

import de.kallifabio.party.handler.PartyHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class playerdisconnectListener implements Listener {

    @EventHandler
    public void on(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();

        PartyHandler party = new PartyHandler();
        if (party.getParty(p) != null) {
            if (party.getParty(p).getOwner() == p) {
                party.disband(p);
            } else {
                party.getParty(p).leave(p);
            }
        }
    }
}
