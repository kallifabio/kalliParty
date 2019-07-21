package de.kallifabio.party.commands;

import de.kallifabio.party.handler.PartyHandler;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class partyCommand extends Command {

    public partyCommand(String name) {
        super(name);
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0) {
                sender.sendMessage(" ");
                sender.sendMessage("§6/party create - §eErstelle eine Party");
                sender.sendMessage("§6/party disband - §eSchließe deine Party");
                sender.sendMessage("§6/party invite <player> - §eLade einen Spieler zu deiner Party ein");
                sender.sendMessage("§6/party kick <player> - §eKicke einen Spieler aus deiner Party");
                sender.sendMessage("§6/party chat <message> - §eSchreibe mit Partymitgliedern");
                sender.sendMessage("§6/party accept <player> - §eAkzeptiere eine Partyeinladung");
                sender.sendMessage("§6/party leave - §eVerlasse deine Party");
                sender.sendMessage("§6/party info - §eInformation über Party §8- §4Coming Soon");
                sender.sendMessage("§6/party setowner - §eSetze den Owner für die Party §8- §4Coming Soon");
                sender.sendMessage(" ");
            } else {
                PartyHandler partyHandler = new PartyHandler();
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("create")) {
                        if (partyHandler.getParty(p) == null) {
                            partyHandler.create(p);
                        } else {
                            p.sendMessage(new ComponentBuilder("§cDu bist bereits in einer Party!").create());
                        }
                    }
                    if (args[0].equalsIgnoreCase("disband")) {
                        if (partyHandler.getParty(p) != null) {
                            partyHandler.disband(p);
                        }
                    }
                    if (args[0].equalsIgnoreCase("accept")) {
                        p.sendMessage(new ComponentBuilder("§cDu musst einen Spieler angeben! /party accept <player>").create());
                    }
                    if (args[0].equalsIgnoreCase("kick")) {
                        p.sendMessage(new ComponentBuilder("§cDu musst einen Spieler angeben! /party kick <player>").create());
                    }
                    if (args[0].equalsIgnoreCase("leave")) {
                        if (partyHandler.getParty(p) != null) {
                            p.sendMessage(new ComponentBuilder("§aDu hast die Party von §6" + partyHandler.getParty(p).getOwner().getName() + " §averlassen").create());
                            if (partyHandler.getParty(p).getOwner() == p) {
                                partyHandler.disband(p);
                            } else {
                                partyHandler.getParty(p).leave(p);
                            }
                        } else {
                            p.sendMessage(new ComponentBuilder("§cDu bist in keiner Party!").create());
                        }
                    }
                    if (args[0].equalsIgnoreCase("chat")) {
                        p.sendMessage(new ComponentBuilder("§cDu musst eine Nachricht eingeben!").create());
                    }
                } else if (args.length > 1) {
                    if (args[0].equalsIgnoreCase("chat")) {
                        String msg = "";
                        for (int i = 1; i < args.length; i++) {
                            msg = msg + args[i] + " ";
                        }
                        partyHandler.getParty(p).sendPartyMessage("§e" + p.getName() + "§8§ §6" + msg);
                    }
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("accept")) {
                            ProxiedPlayer player = BungeeCord.getInstance().getPlayer(args[1]);
                            if (player != null) {
                                if (partyHandler.getParty(player).getInvites().contains(p)) {
                                    partyHandler.getParty(player).add(p);
                                    p.sendMessage(new ComponentBuilder("§aDu hast die Party von §6" + partyHandler.getParty(p).getOwner().getName() + " §abetreten").create());
                                } else {
                                    p.sendMessage(new ComponentBuilder("§cDu bist nicht zu dem Spieler seiner Party eingeladen!").create());
                                }
                            } else {
                                p.sendMessage(new ComponentBuilder("§cSpieler wurde nicht gefunden.").create());
                            }
                        }
                        if (args[0].equalsIgnoreCase("kick")) {
                            ProxiedPlayer player = BungeeCord.getInstance().getPlayer(args[1]);
                            if (player != null) {
                                if (partyHandler.getParty(p).getMembers().contains(player)) {
                                    if (partyHandler.getParty(p).getOwner() == p) {
                                        p.sendMessage(new ComponentBuilder("§c§lDu wurdest aus §6§l" + p.getName() + "§c§lseiner Party gekickt").create());

                                        partyHandler.getParty(player).leave(p);
                                    } else {
                                        p.sendMessage(new ComponentBuilder("§cDu bist nicht der Owner dieser Party!").create());
                                    }
                                }
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("invite")) {
                        ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(new ComponentBuilder("§cSpieler wurde nicht gefunden.").create());
                            return;
                        }
                        if (partyHandler.getParty(p).getOwner() != target) {
                            if (partyHandler.getParty(p).getOwner() == p) {
                                partyHandler.getParty(p).invite(target);
                            }
                        }
                    }
                }
            }
            // TODO /party setowner
            // TODO party info
        }
    }
}
