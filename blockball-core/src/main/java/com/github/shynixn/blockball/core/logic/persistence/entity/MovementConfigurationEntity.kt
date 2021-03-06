package com.github.shynixn.blockball.core.logic.persistence.entity

import com.github.shynixn.blockball.api.business.annotation.YamlSerialize
import com.github.shynixn.blockball.api.persistence.entity.MovementConfiguration

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
class MovementConfigurationEntity : MovementConfiguration {
    /**
     * Bounceeffect from default objects.
     */
    @YamlSerialize(orderNumber = 1, value = "bouncing")
    override var defaultBounceModifier: Double = 1.0
    /**
     * The gravity modifier how fast a ball falls to the ground after being kicked or
     * thrown in to the sky.
     */
    @YamlSerialize(orderNumber = 2, value = "gravity")
    override var gravityModifier: Double = 0.7
    /**
     * Rolling distance.
     */
    @YamlSerialize(orderNumber = 3, value = "rolling-distance")
    override var rollingDistanceModifier: Double = 1.5
    /**
     * Horizontal touch modifier.
     */
    @YamlSerialize(orderNumber = 4, value = "horizontal-touch")
    override var horizontalTouchModifier: Double = 1.0
    /**
     * Vertical touch modifier.
     */
    @YamlSerialize(orderNumber = 5, value = "vertical-touch")
    override var verticalTouchModifier: Double = 1.0
    /**
     * Horizontal kick modifier.
     */
    @YamlSerialize(orderNumber = 6, value = "horizontal-kick")
    override var horizontalKickModifier: Double = 1.5
    /**
     * Vertical kick modifier.
     */
    @YamlSerialize(orderNumber = 7, value = "vertical-kick")
    override var verticalKickModifier: Double = 6.0
    /**
     * Horizontal kick modifier.
     */
    @YamlSerialize(orderNumber = 8, value = "horizontal-throw")
    override var horizontalThrowModifier: Double = 1.0
    /**
     * Vertical kick modifier.
     */
    @YamlSerialize(orderNumber = 9, value = "vertical-throw")
    override var verticalThrowModifier: Double = 1.0
}