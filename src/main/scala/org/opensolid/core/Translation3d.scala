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

final case class Translation3d(vector: Vector3d) extends Transformation3d {
  def this(x: Double, y: Double, z: Double) = this(Vector3d(x, y, z))

  def this(axis: Axis3d, distance: Double) = this(distance * axis.direction)

  override def apply(point: Point3d): Point3d = point + vector

  override def apply(vector: Vector3d): Vector3d = vector
}

object Translation3d {
  def apply(x: Double, y: Double, z: Double): Translation3d = new Translation3d(x, y, z)

  def apply(axis: Axis3d, distance: Double): Translation3d = new Translation3d(axis, distance)
}
