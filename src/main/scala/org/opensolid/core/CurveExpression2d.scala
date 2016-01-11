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

abstract class CurveExpression2d {
  import CurveExpression2d._

  def evaluate(parameterValue: Double): Point2d

  def bounds(parameterBounds: Interval): Box2d

  def tangentDirection: DirectionCurveExpression2d

  def normalDirection: DirectionCurveExpression2d

  def curvature: ScalarCurveExpression
}

object CurveExpression2d {
  case class Constant(point: Point2d) extends CurveExpression2d {
    private[this] def box = Box2d(point.components.map(Interval(_)))

    def evaluate(parameterValue: Double): Point2d = point

    def bounds(parameterBounds: Interval): Box2d = box

    def tangentDirection: DirectionCurveExpression2d =
      DirectionCurveExpression2d.Constant(Direction2d.None)

    def normalDirection: DirectionCurveExpression2d =
      DirectionCurveExpression2d.Constant(Direction2d.None)

    def curvature: ScalarCurveExpression = ScalarCurveExpression.constant(0.0)
  }
}