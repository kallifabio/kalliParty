package de.kallifabio.party;

import de.kallifabio.party.commands.partyCommand;
import de.kallifabio.party.listener.partyserverswitchListener;
import de.kallifabio.party.listener.playerdisconnectListener;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class kalliParty extends Plugin {

    private static kalliParty instance;

    private static String prefix = "§bkalli§3Party §8│";

    @Override
    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerListener(this, new playerdisconnectListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new partyserverswitchListener());

        getProxy().getPluginManager().registerCommand(this, new partyCommand("party"));

        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§2Das Plugin wurde gestartet"));
    }

    @Override
    public void onDisable() {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§4Das Plugin wurde gestoppt"));
    }

    public static kalliParty getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }
}
