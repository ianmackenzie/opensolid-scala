////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//  OpenSolid is a generic library for the representation and manipulation    //
//  of geometric objects such as points, curves, surfaces, and volumes.       //
//                                                                            //
//  Copyright 2007-2015 by Ian Mackenzie                                      //
//  ian.e.mackenzie@gmail.com                                                 //
//                                                                            //
//  This Source Code Form is subject to the terms of the Mozilla Public       //
//  License, v. 2.0. If a copy of the MPL was not distributed with this file, //
//  you can obtain one at http://mozilla.org/MPL/2.0/.                        //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

package org.opensolid.core

import scala.annotation.tailrec
import scala.math
import scala.util.Random

final case class Direction3d(vector: Vector3d) extends VectorTransformable3d[Direction3d] {
  def this(x: Double, y: Double, z: Double) =
    this(Vector3d(x, y, z))

  def x: Double =
    vector.x

  def y: Double =
    vector.y

  def z: Double =
    vector.z

  def components: (Double, Double, Double) =
    vector.components

  def component(index: Int): Double =
    vector.component(index)

  def unary_- : Direction3d =
    Direction3d(-vector)

  def negated: Direction3d =
    -this

  def *(value: Double): Vector3d =
    vector * value

  def times(value: Double): Vector3d =
    this * value

  def transformedBy(transformation: Transformation3d): Direction3d =
    Direction3d(vector.transformedBy(transformation))

  def projectedOnto(plane: Plane3d): Direction3d =
    vector.projectedOnto(plane).direction

  def projectedInto(plane: Plane3d): Direction2d =
    vector.projectedInto(plane).direction

  def normalDirection: Direction3d =
    vector.normalDirection

  def angleTo(that: Direction3d): Double =
    math.acos(this.vector.dot(that.vector))
}

object Direction3d {
  def apply(x: Double, y: Double, z: Double): Direction3d =
    Direction3d(Vector3d(x, y, z))

  def fromComponents(components: (Double, Double, Double)): Direction3d =
    Direction3d(Vector3d.fromComponents(components))

  def spherical(azimuth: Double, elevation: Double): Direction3d = {
    val cosElevation = math.cos(elevation)
    val sinElevation = math.sin(elevation)
    val cosAzimuth = math.cos(azimuth)
    val sinAzimuth = math.sin(azimuth)
    Direction3d(cosElevation * cosAzimuth, cosElevation * sinAzimuth, sinElevation)
  }

  def random: Direction3d =
    random(Random)

  def random(generator: Random): Direction3d = {
    @tailrec
    def generate: Direction3d = {
      val x = -1.0 + 2.0 * generator.nextDouble
      val y = -1.0 + 2.0 * generator.nextDouble
      val z = -1.0 + 2.0 * generator.nextDouble
      val squaredNorm = x * x + y * y + z * z
      if (squaredNorm >= 0.25 && squaredNorm <= 1.0) {
        val norm = math.sqrt(squaredNorm)
        Direction3d(x / norm, y / norm, z / norm)
      } else {
        generate
      }
    }
    generate
  }

  val None: Direction3d = Direction3d(0.0, 0.0, 0.0)

  val X: Direction3d = Direction3d(1.0, 0.0, 0.0)

  val Y: Direction3d = Direction3d(0.0, 1.0, 0.0)

  val Z: Direction3d = Direction3d(0.0, 0.0, 1.0)
}
