(ns gen.dynamic
  (:require [clojure.math :as math]
            [clojure.set :as set]
            [clojure.walk :as walk]
            [gen]
            [gen.choice-map :as choice-map]
            [gen.dynamic.choice-map :as dynamic.choice-map]
            [gen.dynamic.trace :as dynamic.trace]
            [gen.generative-function :as gf]
            [gen.trace :as trace])
  (:import [gen.dynamic.trace Trace]))

(set! *warn-on-reflection* true)

(defrecord DynamicDSLFunction [^clojure.lang.IFn clojure-fn]
  clojure.lang.IFn
  (invoke [_] (dynamic.trace/without-tracing (clojure-fn)))
  (invoke [_ arg1] (dynamic.trace/without-tracing (clojure-fn arg1)))
  (invoke [_ arg1 arg2] (dynamic.trace/without-tracing (clojure-fn arg1 arg2)))
  (invoke [_ arg1 arg2 arg3] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3)))
  (invoke [_ arg1 arg2 arg3 arg4] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18 s] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18 s)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18 arg19 arg20] (dynamic.trace/without-tracing (clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18 arg19 arg20)))
  (invoke [_ arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18 arg19 arg20 args] (apply clojure-fn arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10 arg11 arg12 arg13 arg14 arg15 arg16 arg17 arg18 arg19 arg20 args))
  (applyTo [_ arglist] (dynamic.trace/without-tracing (.applyTo clojure-fn arglist)))

  gf/Simulate
  (simulate [gf args]
    (let [trace (atom (dynamic.trace/trace gf args))]
      (binding [dynamic.trace/*splice*
                (fn [gf args]
                  (let [subtrace (gf/simulate gf args)]
                    (swap! trace dynamic.trace/merge-subtraces subtrace)
                    (trace/retval subtrace)))

                dynamic.trace/*trace*
                (fn [k gf args]
                  (let [subtrace (gf/simulate gf args)]
                    (swap! trace dynamic.trace/assoc-subtrace k subtrace)
                    (trace/retval subtrace)))]
        (let [retval (apply clojure-fn args)]
          (swap! trace dynamic.trace/set-retval! retval)
          @trace))))

  gf/Generate
  (generate [gf args]
    (let [trace (gf/simulate gf args)]
      {:trace trace :weight (math/log 1)}))
  (generate [gf args constraints]
    (let [state (atom {:trace (dynamic.trace/trace gf args)
                       :weight 0})]
      (binding [dynamic.trace/*splice*
                (fn [gf args]
                  (let [{subtrace :trace
                         weight :weight}
                        (gf/generate gf args constraints)]
                    (swap! state update :trace dynamic.trace/merge-subtraces subtrace)
                    (swap! state update :weight + weight)
                    (trace/retval subtrace)))

                dynamic.trace/*trace*
                (fn [k gf args]
                  (let [{subtrace :trace
                         weight :weight}
                        (if-let [constraints (get (choice-map/submaps constraints)
                                                  k)]
                          (gf/generate gf args constraints)
                          (gf/generate gf args))]
                    (swap! state update :trace dynamic.trace/assoc-subtrace k subtrace)
                    (swap! state update :weight + weight)
                    (trace/retval subtrace)))]
        (let [retval (apply clojure-fn args)
              trace (:trace @state)]
          (dynamic.trace/set-retval! trace retval)
          {:trace trace
           :weight (:weight @state)})))))

(extend-type Trace
  trace/Update
  (update [prev-trace constraints]
    (let [^DynamicDSLFunction gf (trace/gf prev-trace)
          state (atom {:trace (dynamic.trace/trace gf (trace/args prev-trace))
                       :weight 0
                       :discard (dynamic.choice-map/choice-map)})]
      (binding [dynamic.trace/*splice* (fn [k gf args]
                                         (let [{subtrace :trace
                                                weight :weight
                                                discard :discard}
                                               (if-let [prev-subtrace (get (.-subtraces prev-trace) k)]
                                                 (let [{new-subtrace :trace
                                                        new-weight :weight
                                                        discard :discard}
                                                       (trace/update prev-subtrace
                                                                     (get (choice-map/submaps constraints)
                                                                          k))]
                                                   {:trace new-subtrace
                                                    :weight new-weight
                                                    :discard discard})
                                                 (gf/generate gf args (get (choice-map/submaps constraints)
                                                                           k)))]
                                           (swap! state update :trace merge subtrace)
                                           (swap! state update :weight + weight)
                                           (when discard
                                             (swap! state update :discard assoc k discard))
                                           (trace/retval subtrace)))
                dynamic.trace/*trace* (fn [k gf args]
                                        (let [{subtrace :trace
                                               weight :weight
                                               discard :discard}
                                              (if-let [prev-subtrace (get (.-subtraces prev-trace) k)]
                                                (let [{new-subtrace :trace
                                                       new-weight :weight
                                                       discard :discard}
                                                      (trace/update prev-subtrace
                                                                    (get (choice-map/submaps constraints)
                                                                         k))]
                                                  {:trace new-subtrace
                                                   :weight new-weight
                                                   :discard discard})
                                                (gf/generate gf args (get (choice-map/submaps constraints)
                                                                          k)))]
                                          (swap! state update :trace dynamic.trace/assoc-subtrace k subtrace)
                                          (swap! state update :weight + weight)
                                          (when discard
                                            (swap! state update :discard assoc k discard))
                                          (trace/retval subtrace)))]
        (let [retval (apply (.-clojure-fn gf)
                            (trace/args prev-trace))
              {:keys [trace weight discard]} @state
              unvisited (select-keys (trace/choices prev-trace)
                                     (set/difference (set (keys (trace/choices prev-trace)))
                                                     (set (keys (trace/choices trace)))))]
          (dynamic.trace/set-retval! trace retval)
          {:trace trace
           :weight weight
           :discard (merge discard unvisited)})))))

(defn trace-form?
  "Returns true if `form` is a trace form."
  [form]
  (and (seq? form)
       (= `gen/trace (first form))))

(defn splice-form?
  "Returns true if `form` is a splice form."
  [form]
  (and (seq? form)
       (= `gen/splice (first form))))

(defn valid-trace-form?
  [form]
  (and (trace-form? form)
       (>= (count form) 3)
       (let [call (last form)]
         (and (seq? call)
              (let [gfn (first call)]
                (or (symbol? gfn)
                    (and (seq gfn)
                         (= `gen (first gfn)))))))))

(defn valid-splice-form?
  [form]
  (and (splice-form? form)
       (>= (count form) 2)
       (let [call (last form)]
         (and (seq? call)
              (let [gfn (first call)]
                (or (symbol? gfn)
                    (and (seq gfn)
                         (= `gen (first gfn)))))))))

(defmacro gen
  "Defines a generative function."
  [& args]
  (let [name (when (simple-symbol? (first args))
               (first args))
        [params & body] (if name (rest args) args)]
    `(->DynamicDSLFunction
      (fn ~@(when name [name])
        ~params
        ~@(walk/postwalk (fn [form]
                           (cond (trace-form? form)
                                 (if-not (valid-trace-form? form)
                                   (throw (ex-info "Malformed trace expression." {:form form}))
                                   (let [[addr [gf & args]] (rest form)]
                                     `(dynamic.trace/*trace* ~addr ~gf ~(vec args))))

                                 (splice-form? form)
                                 (if-not (valid-splice-form? form)
                                   (throw (ex-info "Malformed splice expression." {:form form}))
                                   (let [[[gf & args]] (rest form)]
                                     `(dynamic.trace/*splice* ~gf ~(vec args))))

                                 :else
                                 form))
                         body)))))

(defmacro let-traced
  [bindings & body]
  (let [bents (partition 2 bindings)]
    (assert (every? symbol? (map first bents)))
    `(let ~(into []
                 (mapcat (fn [[sym expr]]
                           [sym `(gen/trace (quote ~sym) ~expr)]))
                 bents)
       ~@body)))
