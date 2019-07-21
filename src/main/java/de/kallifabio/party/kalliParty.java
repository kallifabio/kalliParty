package de.kallifabio.party;

import de.kallifabio.party.commands.partyCommand;
import de.kallifabio.party.listener.partyserverswitchListener;
import de.kallifabio.party.listener.playerdisconnectListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class kalliParty extends Plugin {

    private static kalliParty instance;

    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerListener(this, new playerdisconnectListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new partyserverswitchListener());

        getProxy().getPluginManager().registerCommand(this, new partyCommand("party"));
    }

    public static kalliParty getInstance() {
        return instance;
    }

    // TODO Prefix
    // TODO Design(Farben im Chat)
}
