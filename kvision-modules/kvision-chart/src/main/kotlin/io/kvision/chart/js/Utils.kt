/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED_TYPEALIAS_PARAMETER")

package io.kvision.chart.js

typealias DeepPartial<T> = Any

typealias _DeepPartialArray<T> = Array<DeepPartial<T>>

typealias _DeepPartialObject<T> = Any

typealias DistributiveArray<T> = Any

typealias UnionToIntersection<U> = Any

typealias ScriptableOptions<T, TContext> = Any

typealias ScriptableAndArrayOptions<T, TContext> = Any

typealias ScatterControllerChartOptions = LineControllerChartOptions

typealias ScatterControllerDatasetOptions = LineControllerDatasetOptions

typealias DoughnutDataPoint = Number

typealias PieControllerDatasetOptions = DoughnutControllerDatasetOptions

typealias PieControllerChartOptions = DoughnutControllerChartOptions

typealias PieAnimationOptions = DoughnutAnimationOptions

typealias PieDataPoint = DoughnutDataPoint

typealias PieMetaExtensions = DoughnutMetaExtensions

typealias PolarAreaAnimationOptions = DoughnutAnimationOptions

typealias RadarControllerChartOptions = LineControllerChartOptions

typealias Overrides = Any

typealias InteractionModeFunction = (chart: Chart, e: ChartEvent, options: InteractionOptions, useFinalPosition: Boolean) -> Array<InteractionItem>

typealias ScaleOptions<TScale> = DeepPartial<Any>

typealias DatasetChartOptions<TType> = Any

typealias ChartOptions = DeepPartial<CoreChartOptions /* CoreChartOptions<TType> & ElementChartOptions<TType> & PluginChartOptions<TType> & DatasetChartOptions<TType> & ScaleChartOptions<TType> & dynamic */>

typealias DefaultDataPoint<TType> = DistributiveArray<Any>
