package de.kallifabio.party.manager;

import de.kallifabio.party.handler.PartyHandler;
import de.kallifabio.party.kalliParty;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PartyManager {

    private ProxiedPlayer owner;
    private List<ProxiedPlayer> party;
    private List<ProxiedPlayer> invited;

    public PartyManager(ProxiedPlayer owner) {
        this.owner = owner;
        this.party = new ArrayList();
        this.invited = new ArrayList();
    }

    public void leave(ProxiedPlayer p) {
        if (this.party.contains(p)) {
            if (this.owner == p) {
                PartyHandler partyHandler = new PartyHandler();

                partyHandler.disband(p);

                this.party.remove(p);
                this.invited.remove(p);
            } else {
                getOwner().sendMessage(new ComponentBuilder("§6" + p.getName() + " §ehat die Party verlassen").create());
                for (ProxiedPlayer members : getMembers()) {
                    members.sendMessage(new ComponentBuilder("§6" + p.getName() + " §ehat die Party verlassen").create());
                }
                this.party.remove(p);
                this.invited.remove(p);
            }
        }
    }

    public void add(ProxiedPlayer p) {
        this.party.add(p);
    }

    public ProxiedPlayer getOwner() {
        return this.owner;
    }

    public boolean hasParty(ProxiedPlayer p) {
        if ((this.party.contains(p)) || (this.owner == p)) {
            return true;
        }
        return false;
    }

    public void sendPartyMessage(String message) {
        getOwner().sendMessage(new ComponentBuilder(message).create());
        for (ProxiedPlayer members : getMembers()) {
            members.sendMessage(new ComponentBuilder(message).create());
        }
    }

    public void invite(final ProxiedPlayer p) {
        PartyHandler partyHandler = new PartyHandler();
        for (PartyManager parties : partyHandler.getAllParties()) {
            if (!parties.getInvites().contains(p)) {
                if (!this.invited.contains(p)) {
                    this.invited.add(p);

                    this.owner.sendMessage(new ComponentBuilder("§aDu hast §6" + p.getName() + " §ain deine Party eingeladen. Er hat §e30 sekunden §azum akzeptieren.").create());
                    p.sendMessage(new ComponentBuilder("§aDu wurdest zu §6" + this.owner.getName() + " §aseiner Party eingeladen. Benutze §b/party accept §b" + this.owner.getName() + " §azum akzeptieren.").create());

                    BungeeCord.getInstance().getScheduler().schedule(kalliParty.getInstance(), new Runnable() {
                        public void run() {
                            PartyManager.this.invited.remove(p);

                            PartyManager.this.getOwner().sendMessage(new ComponentBuilder("§e" + p.getName() + " §cbracuhte zulange zum akteptieren!").create());
                            p.sendMessage(new ComponentBuilder("§cDu hast zulange zum akzeptieren auf die Einladung von §e" + PartyManager.this.getOwner().getName() + " §gebraucht!").create());
                        }
                    }, 30L, TimeUnit.SECONDS);
                }
            } else if (this.invited.contains(p)) {
                this.owner.sendMessage(new ComponentBuilder("§cDu hast diesen Spieler bereits in deine Party eingeladen").create());
            } else {
                this.owner.sendMessage(new ComponentBuilder("§cDer Spieler wurde bereits zu einer anderen Party eingeladen").create());
            }
        }
    }

    public List<ProxiedPlayer> getMembers() {
        return this.party;
    }

    public List<ProxiedPlayer> getInvites() {
        return this.invited;
    }
}
