// package com.picsel.app.scheduled;

// import org.springframework.scheduling.annotation.Async;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import ai.djl.inference.Predictor;
// import ai.djl.repository.zoo.ZooModel;
// import ai.djl.translate.TranslateException;

// @Component
// public class LstmScheduler {
// // TODO: Voir si ça vaut plus le coup de charger le modèle à chaque fois ou
// de
// // le garder chargé
// private final ZooModel<String, String> model;

// public LstmScheduler(ZooModel<String, String> model) {
// this.model = model;
// }

// @Scheduled(fixedDelay = 1000)
// @Async
// public String infer() {
// try (Predictor<String, String> predictor = model.newPredictor()) {
// String input = "";

// return predictor.predict(input);
// } catch (TranslateException e) {
// throw new IllegalStateException("Failed to run LSTM inference", e);
// }
// }
// }
