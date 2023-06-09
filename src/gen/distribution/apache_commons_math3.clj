(ns gen.distribution.apache-commons-math3
  (:import [org.apache.commons.math3.distribution BetaDistribution]
           [org.apache.commons.math3.distribution GammaDistribution]
           [org.apache.commons.math3.distribution AbstractRealDistribution])
  (:require [gen.choice-map :as choice-map]
            [gen.dynamic.choice-map :as dynamic.choice-map]
            [gen.generative-function :as gf]
            [gen.trace :as trace]))

(set! *warn-on-reflection* true)

(defprotocol LogPDF
  (logpdf [_ v]))

(defprotocol Sample
  (sample [_]))

(extend-type AbstractRealDistribution
  LogPDF
  (logpdf [^AbstractRealDistribution obj v]
    (.logDensity obj v))

  Sample
  (sample [^AbstractRealDistribution obj]
    (.sample obj)))

(declare ->Trace)

(defrecord Distribution [constructor]
  clojure.lang.IFn
  (invoke [_ arg1]
    (let [^AbstractRealDistribution distribution (constructor arg1)]
      (.sample distribution)))
  (invoke [_ arg1 arg2]
    (let [^AbstractRealDistribution distribution (constructor arg1 arg2)]
      (.sample distribution)))
  (applyTo [_ arglist]
    (let [^AbstractRealDistribution distribution (apply constructor arglist)]
      (.sample distribution)))

  gf/Simulate
  (simulate [this args]
    (let [distribution (apply constructor args)
          sample (sample distribution)
          score (logpdf distribution sample)]
      (->Trace this args sample score)))

  gf/Generate
  (generate [gf args]
    {:weight 0
     :trace (gf/simulate gf args)})
  (generate [gf args constraints]
    (assert (dynamic.choice-map/choice? constraints))
    (let [retval (choice-map/value constraints)
          distribution (apply constructor args)
          weight (logpdf distribution retval)
          trace (->Trace gf args retval weight)]
      {:weight weight
       :trace trace})))

(defrecord Trace [^Distribution distribution args retval score]
  trace/Args
  (args [_] args)

  trace/Choices
  (choices [_]
    (dynamic.choice-map/choice retval))

  trace/GenFn
  (gf [_] distribution)

  trace/RetVal
  (retval [_] retval)

  trace/Score
  (score [_]
    score)

  trace/Update
  (update [prev-trace constraints]
    (cond (dynamic.choice-map/choice-map? constraints)
          (throw (ex-info "Expected a value at address but found a sub-assignment."
                          {:sub-assignment constraints}))

          (dynamic.choice-map/choice? constraints)
          (-> (gf/generate distribution (trace/args prev-trace) constraints)
              (update :weight - (trace/score prev-trace))
              (assoc :discard (dynamic.choice-map/choice (trace/retval prev-trace))))

          :else
          {:trace prev-trace
           :weight (let [distribution (apply (.-constructor distribution)
                                             (trace/args prev-trace))]
                     (- (logpdf distribution (trace/retval prev-trace))
                        (trace/score prev-trace)))})))

(def beta
  (Distribution.
   (fn [alpha beta]
     (BetaDistribution. alpha beta))))

(def gamma
  (Distribution.
   (fn [shape scale]
     (GammaDistribution. shape scale))))
