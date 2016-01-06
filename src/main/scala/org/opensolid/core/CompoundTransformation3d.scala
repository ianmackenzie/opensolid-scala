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

final case class CompoundTransformation3d(first: Transformation3d, second: Transformation3d)
  extends Transformation3d {

  override def apply(handedness: Handedness): Handedness = second(first(handedness))

  override def apply(point: Point3d): Point3d = second(first(point))

  override def apply(vector: Vector3d): Vector3d = second(first(vector))

  override def apply(direction: Direction3d): Direction3d = second(first(direction))
}