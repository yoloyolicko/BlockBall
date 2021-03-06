package com.github.shynixn.blockball.api.persistence.entity

import com.github.shynixn.blockball.api.business.enumeration.GameType
import com.github.shynixn.blockball.api.business.enumeration.Team
import java.util.*

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
interface GameStorage {

    /** Team of the player. */
    var team: Team?

    /**
     * Exp level of the player.
     */
    var level: Int

    /**
     * Actual exp of the player.
     */
    var exp: Float

    /**
     * Owner uuid.
     */
    var uuid: UUID

    /**
     * Max health of the player.
     */
    var maxHealth: Double

    /**
     * Health of the player.
     */
    var health: Double

    /**
     * Hunger of the player.
     */
    var hunger: Int

    /**
     * Storage belongs to this [GameType].
     */
    var gameType: GameType

    /**
     * Gamemode of the player.
     */
    var gameMode: Any

    /**
     * Scoreboard of the player.
     */
    var scoreboard: Any

    /**
     * Walking Speed of the player.
     */
    var walkingSpeed: Float

    /**
     * Was the player flying?
     */
    var flying: Boolean

    /**
     * Was the player allowed to fly?
     */
    var allowedFlying: Boolean

    /**
     * Inventory cache.
     */
    var inventoryContents: Array<Any?>

    /**
     * Inventory armor cache.
     */
    var armorContents: Array<Any?>
}