package com.github.shynixn.blockball.bukkit.logic.business.service

import com.github.shynixn.blockball.api.business.enumeration.PluginDependency
import com.github.shynixn.blockball.api.business.service.DependencyPlaceholderApiService
import com.github.shynixn.blockball.api.business.service.DependencyService
import com.github.shynixn.blockball.bukkit.logic.business.nms.VersionSupport
import com.google.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin
import java.util.logging.Level

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
class DependencyServiceImpl @Inject constructor(private val plugin: Plugin, private val placeholderApiService: DependencyPlaceholderApiService) : DependencyService {
    private val prefix: String = ChatColor.BLUE.toString() + "[BlockBall] "
    private var printedWorldEditError = false

    /**
     * Checks for installed dependencies and shows console output.
     */
    override fun checkForInstalledDependencies() {
        printInstallment(PluginDependency.WORLEDIT)
        printInstallment(PluginDependency.PLACEHOLDERAPI)
        printInstallment(PluginDependency.VAULT)
        printInstallment(PluginDependency.BOSSBARAPI)

        placeholderApiService.registerListener()
    }

    /**
     * Returns if the given [pluginDependency] is installed.
     */
    override fun isInstalled(pluginDependency: PluginDependency): Boolean {
        val plugin = Bukkit.getPluginManager().getPlugin(pluginDependency.pluginName)

        if (plugin != null && pluginDependency == PluginDependency.WORLEDIT && VersionSupport.getServerVersion().isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
            if (!printedWorldEditError) {
                this.plugin.logger.log(Level.WARNING, "WorldEdit dependency cannot be established in 1.13 yet.")
                printedWorldEditError = true
            }

            return false
        }

        return plugin != null
    }

    /**
     * Prints to the console if the plugin is installed.
     */
    private fun printInstallment(pluginDependency: PluginDependency) {
        if (isInstalled(pluginDependency)) {
            val plugin = Bukkit.getPluginManager().getPlugin(pluginDependency.pluginName)

            Bukkit.getServer().consoleSender.sendMessage(prefix + ChatColor.DARK_GREEN + "found dependency [" + plugin.name + "].")
            Bukkit.getServer().consoleSender.sendMessage(prefix + ChatColor.DARK_GREEN + "successfully loaded dependency [" + plugin.name + "] " + plugin.description.version + '.')
        }
    }
}