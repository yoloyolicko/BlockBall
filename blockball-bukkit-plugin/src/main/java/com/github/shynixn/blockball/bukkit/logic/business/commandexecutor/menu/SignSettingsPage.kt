package com.github.shynixn.blockball.bukkit.logic.business.commandexecutor.menu

import com.github.shynixn.blockball.api.business.enumeration.GameType
import com.github.shynixn.blockball.api.business.service.ConfigurationService
import com.github.shynixn.blockball.api.business.service.RightclickManageService
import com.github.shynixn.blockball.api.persistence.entity.Arena
import com.github.shynixn.blockball.bukkit.logic.business.commandexecutor.menu.BlockBallCommand.*
import com.github.shynixn.blockball.bukkit.logic.business.extension.ChatBuilder
import com.github.shynixn.blockball.bukkit.logic.business.extension.toPosition
import com.github.shynixn.blockball.bukkit.logic.business.extension.toSingleLine
import com.google.inject.Inject
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.block.Sign
import org.bukkit.entity.Player

/**
 * Created by Shynixn 2018.
 * <p>
 * Version 1.2
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2018 by Shynixn
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
class SignSettingsPage @Inject constructor(private val configurationService: ConfigurationService, private val rightclickManageService: RightclickManageService) : Page(SignSettingsPage.ID, MainSettingsPage.ID) {

    companion object {
        /** Id of the page. */
        const val ID = 11
    }

    /**
     * Returns the key of the command when this page should be executed.
     *
     * @return key
     */
    override fun getCommandKey(): PageKey {
        return PageKey.SIGNS
    }


    /**
     * Executes actions for this page.
     *
     * @param cache cache
     */
    override fun execute(player: Player, command: BlockBallCommand, cache: Array<Any?>, args: Array<String>): CommandResult {
        val prefix = configurationService.findValue<String>("messages.prefix")

        val arena = cache[0] as Arena
        when (command) {
            SIGNS_ADDTEAMRED -> {
                player.sendMessage(prefix + "Rightclick on a sign.")
                rightclickManageService.watchForNextRightClickSign<Player, Location>(player, { location ->
                    arena.meta.redTeamMeta.signs.add(location.toPosition())
                    player.sendMessage(prefix + "Save and reload to enable the sign.")
                    showLoadingSign(location)
                })
            }
            SIGNS_ADDTEAMBLUE -> {
                player.sendMessage(prefix + "Rightclick on a sign.")
                rightclickManageService.watchForNextRightClickSign<Player, Location>(player, { location ->
                    arena.meta.blueTeamMeta.signs.add(location.toPosition())
                    player.sendMessage(prefix + "Save and reload to enable the sign.")
                    showLoadingSign(location)
                })
            }
            SIGNS_ADDJOINANY -> {
                player.sendMessage(prefix + "Rightclick on a sign.")
                rightclickManageService.watchForNextRightClickSign<Player, Location>(player, { location ->
                    arena.meta.lobbyMeta.joinSigns.add(location.toPosition())
                    player.sendMessage(prefix + "Save and reload to enable the sign.")
                    showLoadingSign(location)
                })
            }
            SIGNS_LEAVE -> {
                player.sendMessage(prefix + "Rightclick on a sign.")
                rightclickManageService.watchForNextRightClickSign<Player, Location>(player, { location ->
                    arena.meta.lobbyMeta.leaveSigns.add(location.toPosition())
                    player.sendMessage(prefix + "Save and reload to enable the sign.")
                    showLoadingSign(location)
                })
            }
            else -> {
            }
        }
        return super.execute(player, command, cache, args)
    }

    /**
     * Show loading sign.
     */
    private fun showLoadingSign(location: Location) {
        val sign = location.block.state as Sign
        sign.setLine(0, ChatColor.BOLD.toString() + "BlockBall")
        sign.setLine(1, ChatColor.GREEN.toString() + "Loading...")
        sign.update(true)
    }

    /**
     * Builds the page content.
     *
     * @param cache cache
     * @return content
     */
    override fun buildPage(cache: Array<Any?>): ChatBuilder {
        val arena = cache[0] as Arena

        val teamSignsRed = arena.meta.redTeamMeta.signs.map { p -> this.printLocation(p) }
        val teamSignsBlue = arena.meta.blueTeamMeta.signs.map { p -> this.printLocation(p) }
        val joinSigns = arena.meta.lobbyMeta.joinSigns.map { p -> this.printLocation(p) }
        val leaveSigns = arena.meta.lobbyMeta.leaveSigns.map { p -> this.printLocation(p) }

        if (arena.gameType == GameType.HUBGAME) {
            return ChatBuilder()
                    .component("- Signs Team Red: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(teamSignsRed.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_ADDTEAMRED.command)
                    .setHoverText(ChatColor.YELLOW.toString() + "Players clicking this sign automatically join the game and the red team.\n&6&m      \n&rEnables the next sign to be added after you rightclicked it.\nDestroy the sign to remove it.")
                    .builder().nextLine()
                    .component("- Template Signs Team Red: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.redTeamMeta.signLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " red")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
                    .component("- Signs Team Blue: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(teamSignsBlue.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_ADDTEAMBLUE.command)
                    .setHoverText(ChatColor.YELLOW.toString() + "Players clicking this sign automatically join the game and the blue team.\n&6&m      \n&rEnables the next sign to be added after you rightclicked it.\nDestroy the sign to remove it.")
                    .builder().nextLine()
                    .component("- Template Signs Team Blue: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.blueTeamMeta.signLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " blue")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
                    .component("- Signs Join any team: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(joinSigns.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_ADDJOINANY.command)
                    .setHoverText(ChatColor.YELLOW.toString() + "Players clicking this sign automatically join the game and team.\n&6&m      \n&rEnables the next sign to be added after you rightclicked it.\nDestroy the sign to remove it.")
                    .builder().nextLine()
                    .component("- Template Signs Join: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.lobbyMeta.joinSignLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " join")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
                    .component("- Signs Leave: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(leaveSigns.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_LEAVE.command)
                    .setHoverText(ChatColor.YELLOW.toString() + "Players clicking this sign automatically leave the game.\n&6&m      \n&rEnables the next sign to be added after you rightclicked it.\nDestroy the sign to remove it.").builder().nextLine()
                    .component("- Template Signs Leave: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.lobbyMeta.leaveSignLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " leave")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
        } else if (arena.gameType == GameType.MINIGAME || arena.gameType == GameType.BUNGEE) {
            return ChatBuilder()
                    .component("- Signs Team Red: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(teamSignsRed.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_ADDTEAMRED.command)
                    .setHoverText(ChatColor.WHITE.toString() + "Only players in Lobbies can click on this sign and will be queued for this team if the team has not reached it's max amount yet and even teams is disabled.")
                    .builder().nextLine()
                    .component("- Template Signs Team Red: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.redTeamMeta.signLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " red")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
                    .component("- Signs Team Blue: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(teamSignsBlue.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_ADDTEAMBLUE.command)
                    .setHoverText(ChatColor.WHITE.toString() + "Only players in Lobbies can click on this sign and will be queued for this team if the team has not reached it's max amount yet and even teams is disabled.")
                    .builder().nextLine()
                    .component("- Template Signs Team Blue: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.blueTeamMeta.signLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " blue")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
                    .component("- Signs Join any team: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(joinSigns.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_ADDJOINANY.command)
                    .setHoverText(ChatColor.WHITE.toString() + "Players clicking this sign automatically join the game lobby.")
                    .builder().nextLine()
                    .component("- Template Signs Join: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.lobbyMeta.joinSignLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " join")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
                    .component("- Signs Leave: ").builder()
                    .component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(leaveSigns.toSingleLine())
                    .builder()
                    .component(ClickableComponent.ADD.text).setColor(ClickableComponent.ADD.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, SIGNS_LEAVE.command)
                    .setHoverText(ChatColor.WHITE.toString() + "Players clicking this sign automatically leave the game or lobby.").builder().nextLine()
                    .component("- Template Signs Leave: ").builder().component(ClickableComponent.PREVIEW.text).setColor(ClickableComponent.PREVIEW.color)
                    .setHoverText(arena.meta.lobbyMeta.leaveSignLines.toList().toSingleLine()).builder()
                    .component(ClickableComponent.PAGE.text).setColor(ClickableComponent.PAGE.color)
                    .setClickAction(ChatBuilder.ClickAction.RUN_COMMAND, BlockBallCommand.MULTILINES_TEAMSIGNTEMPLATE.command + " leave")
                    .setHoverText("Opens the page to change the template on signs to join this team.")
                    .builder().nextLine()
        }

        return ChatBuilder()
    }
}