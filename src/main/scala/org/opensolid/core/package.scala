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

package org.opensolid

import scala.math

package object core {
  implicit class ImplicitScalar(val value: Double) extends AnyVal {
    def isZero(tolerance: Double): Boolean = value >= -tolerance && value <= tolerance

    def isNotZero(tolerance: Double): Boolean = value < -tolerance || value > tolerance

    def meters: Double = value

    def inMeters: Double = value

    def centimeters: Double = value * 1e-2

    def inCentimeters: Double = value * 1e2

    def millimeters: Double = value * 1e-3

    def inMillimeters: Double = value * 1e3

    def microns: Double = value * 1e-6

    def inMicrons: Double = value * 1e6

    def kilometers: Double = value * 1e3

    def inKilometers: Double = value * 1e-3

    def inches: Double = value * 0.0254

    def inInches: Double = value / 0.0254

    def feet: Double = value * 0.3048

    def inFeet: Double = value / 0.3048

    def thou: Double = value * 0.0000254

    def inThou: Double = value / 0.0000254

    def yards: Double = value * 0.9144

    def inYards: Double = value / 0.9144

    def miles: Double = value * 1609.344

    def inMiles: Double = value / 1609.344

    def radians: Double = value

    def inRadians: Double = value

    def degrees: Double = math.toRadians(value)

    def inDegrees: Double = math.toDegrees(value)

    def *(sign: Sign): Double = value * sign.value

    def +(interval: Interval): Interval = {
      Interval(value + interval.lowerBound, value + interval.upperBound)
    }

    def -(interval: Interval): Interval = {
      Interval(value - interval.upperBound, value - interval.lowerBound)
    }

    def *(interval: Interval): Interval = interval * value

    def /(interval: Interval): Interval = {
      if (interval.isEmpty) {
        Interval.Empty
      } else if (interval.lowerBound > 0.0) {
        if (value >= 0.0) {
          Interval(value / interval.upperBound, value / interval.lowerBound)
        } else {
          Interval(value / interval.lowerBound, value / interval.upperBound)
        }
      } else if (interval.upperBound < 0.0) {
        if (value >= 0.0) {
          Interval(value / interval.upperBound, value / interval.lowerBound)
        } else {
          Interval(value / interval.lowerBound, value / interval.upperBound)
        }
      } else if (value == 0.0) {
        Interval(0.0)
      } else {
        Interval.Whole
      }
    }

    def *(vector: Vector2d): Vector2d = vector * value

    def *(vector: Vector3d): Vector3d = vector * value

    def *(direction: Direction2d): Vector2d = direction * value

    def *(direction: Direction3d): Vector3d = direction * value

    def hull(that: Double): Interval = Interval(value.min(that), value.max(that))

    def hull(interval: Interval): Interval = interval.hull(value)
  }

  implicit class ImplicitPair[T](val tuple: (T, T)) extends AnyVal {
    def first: T = tuple._1

    def second: T = tuple._2

    def map[U](function: (T) => U): (U, U) = (function(first), function(second))

    def foreach(function: (T) => Unit): Unit = {
      function(first)
      function(second)
    }

    def apply(index: Int): T = index match {
      case 0 => first
      case 1 => second
      case _ => throw new IndexOutOfBoundsException(s"Index $index is out of bounds for a pair")
    }

    def count (function: (T) => Boolean): Int =
      (if (function(first)) 1 else 0) + (if (function(second)) 1 else 0)
  }

  implicit class ImplicitTriple[T](val tuple: (T, T, T)) extends AnyVal {
    def first: T = tuple._1

    def second: T = tuple._2

    def third: T = tuple._3

    def map[U](function: (T) => U): (U, U, U) =
      (function(first), function(second), function(third))

    def foreach(function: (T) => Unit): Unit = {
      function(first)
      function(second)
      function(third)
    }

    def apply(index: Int): T = index match {
      case 0 => first
      case 1 => second
      case 2 => third
      case _ => throw new IndexOutOfBoundsException(s"Index $index is out of bounds for a triple")
    }

    def count (function: (T) => Boolean): Int =
      (if (function(first)) 1 else 0) +
      (if (function(second)) 1 else 0) +
      (if (function(third)) 1 else 0)
  }
}