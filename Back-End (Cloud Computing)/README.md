# Blendit (Cloud Computing)
Cloud computing, handles API requests and API responses by processing the logic of the requests sent, and will carry out decision making to handle existing responses, the API can handle post images from clients to be stored in cloud storage or carry out prediction processes with machine models learning is stored in the cloud storage bucket, the entire data is stored in cloud SQL in cloud storage, the data will be displayed based on requests from the client. The backend uses javascript, with the javascript runtime using Node.js, and with the experss.js framework, using several packages that are really needed by the backend application, such as :

- "@google-cloud/storage"

    This package allows your Node.js application to interact with Google Cloud Storage. You can use it to store and retrieve files, such as profile pictures uploaded by users.

- "@tensorflow/tfjs-node"

    This is the Node.js version of TensorFlow.js, which is a library for machine learning in JavaScript. It provides the tools needed to train and deploy machine learning models directly in JavaScript. The Node.js version utilizes TensorFlow backend which can take advantage of the underlying hardware acceleration (like GPUs) to improve performance for training and executing models. It's often used for tasks like image and speech recognition, forecasting, and natural language processing within Node.js applications.

- "bcryptjs"

    bcryptjs is a library for hashing passwords securely. When users register or log in, their passwords should be hashed before storing them in the database. bcryptjs provides a way to securely hash passwords and compare hashed passwords for authentication.

- "cors"

    CORS, or Cross-Origin Resource Sharing, is a security feature of web browsers that restricts web applications from making requests to a domain different from the one which served the initial application. The "cors" package is a middleware for Express that enables you to configure your server to selectively allow certain cross-origin requests. This is crucial for services like APIs, where you might need to allow a web application hosted on one domain to access resources from a server on a different domain.

- "dotenv"

    dotenv is a zero-dependency module that loads environment variables from a .env file into process.env. This is useful for storing sensitive information like API keys or database credentials without hardcoding them into your code.

- "express"

    Express is a web application framework for Node.js. It simplifies the process of building web applications and APIs by providing a robust set of features for handling HTTP requests, routing, middleware, and more.

- "express-validator"

    express-validator is a middleware for Express that provides easy validation of user input. It helps sanitize and validate user data to prevent common security vulnerabilities such as SQL injection and cross-site scripting (XSS).

- "jsonwebtoken"

    jsonwebtoken is a package for generating and verifying JSON Web Tokens (JWT) in Node.js. JWTs are commonly used for authentication and authorization in web applications. You can use them to securely transmit information between parties.

- "multer"

    multer is a middleware for handling multipart/form-data, which is commonly used for uploading files in HTML forms. It allows you to parse and handle file uploads easily in Express applications.

- "mysql2"

    mysql2 is a fast MySQL driver for Node.js. It enables your application to interact with a MySQL database, allowing you to perform CRUD operations (Create, Read, Update, Delete) on user data, such as storing user profiles and credentials.

- "nodemon"

    nodemon is a utility that automatically restarts your Node.js application when file changes are detected. This is particularly useful during development, as it eliminates the need to manually stop and restart the server every time you make changes to your code.

## Architecture
![Architecture](../Images/architecture1.png)
![Architecture](../Images/architecture2.png)

## How to use
The following are the steps you can take if you want to use or run this program locally:
```bash
git clone https://github.com/Danar1111/Blendit.git
```
```bash
cd "Back-End (Cloud Computing)"
``` 
```bash
npm install
``` 
You need credentials.js which is a service account key on the Google Cloud platform that is used to access the cloud storage bucket.
```bash
sudo nano credentials.js
``` 
paste in the file, hit the `ctrl+x`,`Y`,and `enter`.

You also need an `.env` file for development environment needs
```bash
sudo nano .env
```
The following is the form of the .env which must be filled in according to your needs.
```js
DB_HOST=
DB_USER=
DB_PASSWORD=
DB_NAME=
JWT_SECRET=
PORT=
BUCKET_NAME=
MODEL_URL=
```
the final step after filling in all the required environments, can run the following command to run the backend server locally, make sure to use `WSL` or use linux to run this:

```bash
npm run start:dev
```

### Walaa, the server is running


## Documentation

### Endpoint List

- [Register](#register)
- [Login](#login)
- [Change Password](#cange-password)
- [Upload Profile Picture](#upload-profile-picture)
- [Delete Profile Picture](#delete-profile-picture)
- [List All Product](#list-all-product)
- [Find Product](#find-product)
- [Add to Favorite](#add-to-favorite)
- [Remove Favorite](#remove-favorite)
- [List All Favorites](#list-all-favorites)
- [Predictions](#predictions)
- [Get Tutorial](#get-tutorial)
- [Get Recommendations](#get-recommendations)
- [List All Category](#list-all-category)
- [Get Category Tutorial](#get-category-tutorial)

---

#### Register
- URL
    - `/register`
- Method
    - `POST`
- Request Body
    - `email` as `string`, must be unique
    - `username` as `string`
    - `password` as `string`, must be at least 6 characters
- Response
    ```json
    {
        "error": false,
        "message": "User registered"
    }
    ```

#### Login
- URL
    - `/login`
- Method
    - `POST`
- Request Body
    - `email` as `string`
    - `password` as `string`, must be at least 6 characters
- Response
    ```json
    {
        "error": false,
        "message": "Login successful",
        "loginResult": {
            "userId": "user-J0WEK5n55O0=",
            "email": "danar123@gmail.com",
            "username": "testuser",
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVzZXItSjBXRUs1bjU1TzA9IiwiaWF0IjoxNzE2NzI5NDMwLCJleHAiOjE3MTY3MzMwMzB9.ofbDhvUz_8K9pFdZoF-B-qTfooFzPhbAEUvkCcTX7j4",
            "profilePic": null
        }
    }
    ```
- Response after upload profile picture
    ```json
    {
        "error": false,
        "message": "Login successful",
        "loginResult": {
            "userId": "user-J0WEK5n55O0=",
            "email": "danar123@gmail.com",
            "username": "testuser",
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVzZXItSjBXRUs1bjU1TzA9IiwiaWF0IjoxNzE2NzMwNTExLCJleHAiOjE3MTY3MzQxMTF9.047n454gllUpG3q4JtlS9LoFLPfxAx-isna6xFTqiVQ",
            "profilePic": "https://storage.googleapis.com/test-blendit-profilepic/user-J0WEK5n55O0=/profile_picture_1716730501733.jpg"
        }
    }
    ```

#### Cange Password
- URL
    - `/changepassword`
- Method
    - `POST`
- Headers
    - `Authorization`: `<token>`
- Request Body
    - `userId` as `string`
    - `password` as `strng` must be at least 6 characters
- Response
    ```json
    {
        "error": false,
        "message": "Change password success"
    }
    ```

#### Upload Profile Picture
- URL
    - `/upload-profile-picture`
- Method
    - `POST`
- Headers
    - `Authorization`: `<token>`
- Request Body
    - `photo` as `file`, must be a valid image file
- Response
    ```json
    {
        "error": false,
        "message": "File uploaded successfully",
        "photoUrl": "https://storage.googleapis.com/test-blendit-profilepic/user-J0WEK5n55O0=/profile_picture_1716730501733.jpg"
    }
    ```

#### Delete Profile Picture
- URL
    - `/delete-profile-picture`
- Method
    - `DELETE`
- Headers
    - `Authorization`: `<token>`
- Response
    ```json
    {
        "error": false,
        "message": "Profile picture deleted successfully"
    }
    ```

#### List All Product
- URL
    - `/listproduct`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `page` as `number`
- Response
    ```json
    {
        "error": false,
        "message": "Products items fetched successfully",
        "totalItems": 259,
        "totalPages": 13,
        "currentPage": 1,
        "pageSize": 20,
        "items": [
            {
            "id": "001",
            "brand": "somethinc",
            "product_name": "copy paste tinted sunscreen spf 40 pa++++",
            "shade": "c01 perle",
            "type": "tinted sunscreen",
            "undertone": "cool",
            "skintone": "light",
            "makeup_type": "foundation",
            "skin_type": "combination",
            "picture": "https://storage.googleapis.com/blendit-storage/productsPic/001.jpeg"
            },
            {
            "id": "002",
            "brand": "somethinc",
            "product_name":.....
            .....
    ```

#### Find Product
- URL
    - `/findproduct`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `page` as `number`
    - `find` as `string`, must be product name or brand name
- Response
    ```json
    {
        "error": false,
        "message": "Products items fetched successfully",
        "totalItems": 12,
        "totalPages": 1,
        "currentPage": 1,
        "pageSize": 20,
        "items": [
            {
            "id": "001",
            "brand": "somethinc",
            "product_name": "copy paste tinted sunscreen spf 40 pa++++",
            "shade": "c01 perle",
            "type": "tinted sunscreen",
            "undertone": "cool",
            "skintone": "light",
            "makeup_type": "foundation",
            "skin_type": "combination",
            "picture": "https://storage.googleapis.com/blendit-storage/productsPic/001.jpeg"
            },
            {
            "id": "002",
            "brand": "somethinc",
            "product_name":.....
            .....
    ```

#### Add to Favorite
- URL
    - `/addfavorite`
- Method
    - `POST`
- Headers
    - `Authorization`: `<token>`
- Request Body
    - `userId` as `string`, must be valid ID
    - `productId` as `string`, must be valid Product ID
- Response
    ```json
    {
        "error": false,
        "message": "Successfully added to favorites"
    }
    ```

#### Remove Favorite
- URL
    - `/removefavorite`
- Method
    - `DELETE`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `userId` as `string`, must be valid ID
    - `productId` as `string`, must be valid Product ID
- Response
    ```json
    {
        "error": false,
        "message": "Successfully deleted product from favorites"
    }
    ```

#### List All Favorites
- URL
    - `/listfavorite`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `userId` as `string`, must be valid ID
- Response
    ```json
    {
        "error": false,
        "message": "Successfully fetched favorite products",
        "userId": "user-EpOcHMm1mI4=",
        "totalItems": 3,
        "items": [
            {
            "product_id": "001",
            "brand": "somethinc",
            "product_name": "copy paste tinted sunscreen spf 40 pa++++",
            "shade": "c01 perle",
            "type": "tinted sunscreen",
            "undertone": "cool",
            "skintone": "light",
            "makeup_type": "foundation",
            "skin_type": "combination",
            "picture": "https://storage.googleapis.com/blendit-storage/productsPic/001.jpeg"
            },
            {
            "product_id": "002",
            "brand": "somethinc",
            "product_name":.....
            .....
    ```

#### Predictions
- URL
    - `/predict`
- Method
    - `POST`
- Headers
    - `Authorization`: `<token>`
- Request Body
    - `skintone` as `light | light-medium | medium | medium-dark | dark`
    - `undertone` as `warm | cool | neutral`
    - `skin_type` as `oily | dry | combination`
    - `image` as `file`, must be valid image
- Response
    ```json
    {
        "status": "success",
        "message": "Model is predicted successfully.",
        "data": {
            "id": "f8788cdf-bcfa-4fd1-9be6-89b70acdf167",
            "shape": "Heart",
            "skintone": "light",
            "undertone": "cool",
            "skin_type": "oily",
            "description": "Kamu memiliki wajah berbentuk heart dengan dahi yang lebar dan garis rambut yang melengkung, serta rahang dan dagu yang lebih sempit dan membulat tanpa sudut tajam. Bentuk wajah heart sering dianggap menarik karena fitur-fiturnya yang unik dan proporsi yang seimbang. Dengan wajah heart, kamu memiliki fleksibilitas dalam mencoba berbagai tampilan dan gaya riasan yang dapat menonjolkan keindahan alami wajahmu.",
            "confidenceScore": 100,
            "createdAt": "2024-06-20T18:10:47.800Z"
        }
    }
    ```

#### Get Tutorial
- URL
    - `/tutorial`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `shape` as `oval | round | square | oblong | heart`
    - `skintone` as `light | light-medium | medium | medium-dark | dark`
    - `undertone` as `warm | cool | neutral`
    - `skin_type` as `oily | dry | combination`
- Response
    ```json
    {
        "error": false,
        "message": "Get tutorial successful",
        "analystResult": {
            "id": "1140",
            "idRecommendation": "106",
            "facetype": "heart",
            "skin_preparation": "Pada kulit berminyak skin preparation penting untuk dilakukan sebelum mengaplikasikan makeup agar makeup tidak cakey saat digunakan. Bagi kulit berminyak kamu bisa melakukan skin preparation menggunakan basic skincare seperti toner, serum, moisturizer dan sunscreen. Gunakan skincare yang membantu pemulihan kulit, mengecilkan pori-pori, dan jika berjerawat gunakan produk yang dapat mengurangi peradangan. Contoh kandungan yang direkomendasikan pada skincare adalah hyaluronic acid dan niacinamide.",
            "base_makeup": "Untuk kulit berminyak pastikan base makeup yang dapat membuat kulit menjadi matte agar dapat bertahan lama. Primer berfungsi untuk mengontrol minyak di wajah, menyamarkan tampilan pori-pori, serta memberikan hasil akhir matte tanpa kilap. Pusatkan primer pada area T-Zone yang lebih mudah berminyak. Hindari pemakaian primer yang mengandung silikon agar tidak menyumbat pori-pori. Untuk foundation gunakan liquid foundation karena lebih ringan di kulit. Gunakan blush on matte atau powder agar tidak mudah luntur dan foundation tidak bergeser.",
            "eye_makeup": "Untuk pemilihan eyeshadow dengan undertone kulit cool adalah pilih warna yang memiliki pink, merah atau kebiruan ",
            "shade_lipstik": "Untuk pemilihan lip product dengan undertone kulit cool tone adalah pilih warna yang cenderung ungu, merah ceri, mauve, magenta, dan merah tua. Hindari warna dengan hint oranye.",
            "image_base": "https://storage.googleapis.com/blendit-storage/tutorialPic/heart.jpeg",
            "image_eye": "https://storage.googleapis.com/blendit-storage/tutorialPic/alisHeart.jpeg",
            "image_lips": "https://storage.googleapis.com/blendit-storage/tutorialPic/lipstickCool.jpeg"
        }
    }
    ```

#### Get Recommendations
- URL
    - `/recommendation`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `skintone` as `light | light-medium | medium | medium-dark | dark`
    - `undertone` as `warm | cool | neutral`
    - `skin_type` as `oily | dry | combination`
- Response
    ```json
    {
        "error": false,
        "message": "Products items recommendation fetched successfully",
        "totalItems": 22,
        "items": [
            {
            "id": "027",
            "brand": "somethinc",
            "product_name": "hooman breathable cushion cover spf 35 pa++++",
            "shade": "perle",
            "type": "cushion",
            "undertone": "cool",
            "skintone": "light",
            "makeup_type": "foundation",
            "skin_type": "oily",
            "picture": "https://storage.googleapis.com/blendit-storage/productsPic/027.jpeg"
            },
            {
            "id": "028",
            "brand": "somethinc",
            "product_name":.....
            .....
    ```

#### List All Category
- URL
    - `/category`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Response
    ```json
    {
        "error": false,
        "message": "Category fetched successfully",
        "totalItems": 6,
        "items": [
            {
            "id": "001",
            "category": "BOLD",
            "thumbnail": "https://storage.googleapis.com/blendit-storage/categoryPic/thumbnail/bold.jpeg"
            },
            {
            "id": "002",
            "category":.....
            ......
    ```

#### Get Category Tutorial
- URL
    - `/categorytutorial`
- Method
    - `GET`
- Headers
    - `Authorization`: `<token>`
- Request Query Parameters
    - `id` as `number`, must be valid id with format `000`
- Response
    ```json
    {
        "error": false,
        "message": "Category fetched successfully",
        "items": {
            "id": "003",
            "category": "KOREAN",
            "skinPreparation": "Korean makeup looks adalah gaya riasan yang berasal dari riasan idol korea. Penggunaan makeup korea diharapkan dapat memberi looks seperti 'ulzzang' atau istilah populer untuk gadis cantik di korea. Skin preparation pada makeup korea terkenal dengan banyak layering skincare produk. Alasannya adalah supaya kulit terllihat lebih sehat dan glowing tanpa ada pori sedikitpun. Biasanya skin preparation dilakukan dengan menggunakan toner mask atau dapat menggunakan toner yang kita punya dengan kapas lalu didiamkan sekitar 10-30 menit hingga kulit wajah terlihat lembab dan kenyal. ",
            "baseMakeup": "Pemilihan base makeup pada pengaplikasian korean makeup biasanya dimulai dengan menggunakan primer yang menyamarkan pori untu mendapatkan kulit sehat dan lembab. Menggunakan BB cream atau cushion akan menciptakan tampilan yang fresh dan glowing namun tetap menyamarkan ketidaksempurnaan kulit tanpa terlihat berlebihan. Gunakan concealer untuk menutupi kekurangan kulit yang tidak ke cover dengan cushion atau BB cream atau biasa disebut \"spot-concealling\". Penggunaan concealer digunakan juga digunakan dibagian bawah mata dan bagian T-zone. Penggunaan blush on memberikan efek rona alami pada pipi dengan warna-warna soft seperti peach, pink muda, atau coral.",
            "eyeMakeup": "Pemilihan eye makeup untuk tampilan Korean adalah menggunakan warna-warna lembut dan natural. Tidak ada salahnya untuk menambahkan sentuhan shimmer halus pada kelopak mata untuk memberikan efek glowing yang segar. Bentuk alis cenderung rapi dan terlihat alami, dengan garis alis yang tipis dan lembut. Sesuaikan bentuk alis dengan bentuk wajah untuk menciptakan tampilan mata yang besar dan terbuka.",
            "lipsMakeup": "Pemilihan warna lipstik untuk tampilan Korean cenderung warna-warna cerah dan segar seperti merah ceri, pink fuchsia, atau orange muda. Pilihan warna ini akan memberikan tampilan bibir yang sehat dan memukau. Teknik pengaplikasiannya dapat menggunakan lip tint atau lipstik velvet untuk memberikan warna yang intens namun tetap lembut. Gunakan lip liner senada untuk mengunci warna lipstik.",
            "skinPrepPic": "https://storage.googleapis.com/blendit-storage/categoryPic/tutorialPic/skinPrep3.jpeg",
            "baseMakeupPic": "https://storage.googleapis.com/blendit-storage/categoryPic/tutorialPic/baseMakeup3.jpeg",
            "eyeMakeupPic": "https://storage.googleapis.com/blendit-storage/categoryPic/tutorialPic/eyeMakeup3.jpeg",
            "lipsMakeupPic": "https://storage.googleapis.com/blendit-storage/categoryPic/tutorialPic/lipsMakeup3.jpeg",
            "thumbnail": "https://storage.googleapis.com/blendit-storage/categoryPic/thumbnail/korean.jpeg"
        }
    }
    ```

<br>

# Thanks