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

import scala.util.Random

final case class BoundingBox2d(x: Interval, y: Interval) extends Bounded2d {
  def components: Array[Interval] = Array(x, y)

  def component(index: Int): Interval = index match {
    case 0 => x
    case 1 => y
    case _ =>
      throw new IndexOutOfBoundsException(s"Index $index is out of bounds for BoundingBox2d")
  }

  override def bounds: BoundingBox2d = this

  def isEmpty: Boolean = x.isEmpty || y.isEmpty

  def isWhole: Boolean = x.isWhole && y.isWhole

  def isSingleton: Boolean = x.isSingleton && y.isSingleton

  def center: Point2d = Point2d(x.median, y.median)

  def interpolated(u: Double, v: Double): Point2d = Point2d(x.interpolated(u), y.interpolated(v))

  def randomPoint: Point2d = randomPoint(Random)

  def randomPoint(generator: Random): Point2d =
    interpolated(generator.nextDouble, generator.nextDouble)

  def hull(point: Point2d): BoundingBox2d = BoundingBox2d(x.hull(point.x), y.hull(point.y))

  def hull(that: BoundingBox2d): BoundingBox2d =
    BoundingBox2d(this.x.hull(that.x), this.y.hull(that.y))

  def intersection(that: BoundingBox2d): BoundingBox2d = {
    val x = this.x.intersection(that.x)
    val y = this.y.intersection(that.y)
    if (x.isEmpty || y.isEmpty) BoundingBox2d.Empty else BoundingBox2d(x, y)
  }

  def overlaps(that: BoundingBox2d): Boolean = this.x.overlaps(that.x) && this.y.overlaps(that.y)

  def overlaps(that: BoundingBox2d, tolerance: Double): Boolean =
    this.x.overlaps(that.x, tolerance) && this.y.overlaps(that.y, tolerance)

  def contains(point: Point2d): Boolean = x.contains(point.x) && y.contains(point.y)

  def contains(point: Point2d, tolerance: Double): Boolean =
    x.contains(point.x, tolerance) && y.contains(point.y, tolerance)

  def contains(that: BoundingBox2d): Boolean = this.x.contains(that.x) && this.y.contains(that.y)

  def contains(that: BoundingBox2d, tolerance: Double): Boolean =
    this.x.contains(that.x, tolerance) && this.y.contains(that.y, tolerance)

  def +(vector: Vector2d): BoundingBox2d = BoundingBox2d(x + vector.x, y + vector.y)

  def +(vectorBoundingBox: VectorBoundingBox2d): BoundingBox2d =
    BoundingBox2d(x + vectorBoundingBox.x, y + vectorBoundingBox.y)

  def -(vector: Vector2d): BoundingBox2d = BoundingBox2d(x - vector.x, y - vector.y)

  def -(vectorBoundingBox: VectorBoundingBox2d): BoundingBox2d =
    BoundingBox2d(x - vectorBoundingBox.x, y - vectorBoundingBox.y)

  def -(point: Point2d): VectorBoundingBox2d = VectorBoundingBox2d(x - point.x, y - point.y)

  def -(that: BoundingBox2d): VectorBoundingBox2d =
    VectorBoundingBox2d(this.x - that.x, this.y - that.y)

}

object BoundingBox2d {
  def fromComponents[T <% Interval](components: Seq[T]): BoundingBox2d = components match {
    case Seq(x, y) => BoundingBox2d(x, y)
    case _ => throw new IllegalArgumentException("BoundingBox2d requires 2 components")
  }

  val Empty: BoundingBox2d = BoundingBox2d(Interval.Empty, Interval.Empty)

  val Whole: BoundingBox2d = BoundingBox2d(Interval.Whole, Interval.Whole)

  val Unit: BoundingBox2d = BoundingBox2d(Interval.Unit, Interval.Unit)
}