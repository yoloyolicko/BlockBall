package com.github.shynixn.blockball.bukkit.logic.persistence.entity

import com.github.shynixn.blockball.api.persistence.entity.Position
import com.github.shynixn.blockball.bukkit.logic.business.extension.YamlSerializer

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
class PositionEntity : Position {
    /** [worldName] which world the location is. */
    @YamlSerializer.YamlSerialize(orderNumber = 1, value = "world")
    override var worldName: String? = null
    /** [x] coordinate. */
    @YamlSerializer.YamlSerialize(orderNumber = 2, value = "x")
    override var x: Double = 0.0
    /** [y] coordinate. */
    @YamlSerializer.YamlSerialize(orderNumber = 2, value = "y")
    override var y: Double = 0.0
    /** [z] coordinate. */
    @YamlSerializer.YamlSerialize(orderNumber = 2, value = "z")
    override var z: Double = 0.0
    /** [yaw] rotation yaw. */
    @YamlSerializer.YamlSerialize(orderNumber = 2, value = "yaw")
    override var yaw: Double = 0.0
    /** [pitch] rotation pitch. */
    @YamlSerializer.YamlSerialize(orderNumber = 2, value = "pitch")
    override var pitch: Double = 0.0
    /** [blockX] coordinate as Int. */
    override val blockX: Int
        get() = x.toInt()
    /** [blockY] coordinate as Int. */
    override val blockY: Int
        get() = y.toInt()
    /** [blockZ] coordinate as Int. */
    override val blockZ: Int
        get() = z.toInt()

    /**
     * Indicates whether some other object is "equal to" this one. Implementations must fulfil the following
     * requirements:
     *
     * * Reflexive: for any non-null reference value x, x.equals(x) should return true.
     * * Symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
     * * Transitive:  for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true
     * * Consistent:  for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
     *
     * Note that the `==` operator in Kotlin code is translated into a call to [equals] when objects on both sides of the
     * operator are not null.
     */
    override fun equals(other: Any?): Boolean {
        if (other is Position) {
            if (other.x == this.x && other.y == this.y && other.z == this.z) {
                return true
            }
            return false
        }
        return super.equals(other)
    }
}