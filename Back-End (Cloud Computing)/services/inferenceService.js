const tf = require('@tensorflow/tfjs-node');

async function predictClassification(model, imageBuffer) {
    let tensor;
    try {
        tensor = tf.node.decodeImage(imageBuffer, 3)
            .resizeNearestNeighbor([150, 150])
            .expandDims()
            .toFloat()
            .div(tf.scalar(255.0));

        console.log('Tensor shape:', tensor.shape);
    } catch (error) {
        throw new Error('Invalid image format. Please upload JPEG, PNG, GIF, or BMP file.');
    }

    const prediction = model.predict(tensor);
    const score = await prediction.data();
    console.log('Prediction scores:', score);

    const confidenceScore = Math.max(...score) * 100;

    const classes = ['Heart', 'Oblong', 'Oval', 'Round', 'Square'];

    const classResult = tf.argMax(prediction, 1).dataSync()[0];
    const label = classes[classResult];

    let description;

    if (label === 'Heart') {
        description = "Kamu memiliki wajah berbentuk heart dengan dahi yang lebar dan garis rambut yang melengkung, serta rahang dan dagu yang lebih sempit dan membulat tanpa sudut tajam. Bentuk wajah heart sering dianggap menarik karena fitur-fiturnya yang unik dan proporsi yang seimbang. Dengan wajah heart, kamu memiliki fleksibilitas dalam mencoba berbagai tampilan dan gaya riasan yang dapat menonjolkan keindahan alami wajahmu.";
    } else if (label === 'Oblong') {
        description = "Kamu memiliki wajah berbentuk oblong dengan panjang wajah yang lebih dominan dibandingkan lebarnya, serta rahang dan dagu yang halus tanpa sudut tajam. Bentuk wajah oblong sering dianggap elegan karena proporsinya yang panjang dan ramping. Dengan wajah oblong, kamu memiliki fleksibilitas dalam mencoba berbagai tampilan dan gaya riasan yang dapat memperpendek dan menyeimbangkan fitur-fitur alami wajahmu.";
    } else if (label === 'Oval') {
        description = "kamu memiliki wajah oval dengan rahang dan dagu yang membulat tanpa sudut tajam. Bentuk wajah oval sering dianggap ideal karena hampir semua jenis riasan cocok untuk bentuk ini. Dengan wajah oval, kamu memiliki fleksibilitas dalam mencoba berbagai tampilan dan gaya.";
    } else if (label === 'Round') {
        description = "Kamu memiliki wajah berbentuk round dengan garis rambut yang melengkung dan rahang serta dagu yang halus tanpa sudut tajam. Bentuk wajah round memberikan kesan simetris dan penuh, membuatnya terlihat lembut dan ramah. Dengan wajah round, kamu memiliki fleksibilitas dalam mencoba berbagai gaya rambut dan riasan yang dapat menonjolkan fitur-fitur alami wajahmu.";
    } else if (label === 'Square') {
        description = "Kamu memiliki wajah berbentuk square dengan garis rahang yang kuat dan dagu yang lebih tajam tanpa sudut yang lembut. Bentuk wajah square sering dianggap tegas dan menarik karena fitur-fiturnya yang terdefinisi dengan baik. Dengan wajah square, kamu memiliki fleksibilitas dalam mencoba berbagai tampilan dan gaya riasan yang dapat menonjolkan dan memperhalus garis-garis alami wajahmu.";
    }

    return { confidenceScore, label, description };
}

module.exports = predictClassification;
