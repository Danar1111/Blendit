const express = require('express');
const { check, validationResult } = require('express-validator');
const router = express.Router();
const authController = require('../controllers/authController');
const uploadController = require('../controllers/uploadController');
const uploadMiddleware = require('../middlewares/uploadMiddleware');
const authMiddleware = require('../middlewares/authMiddleware');
const deleteController = require('../controllers/deleteController');
const productController = require('../controllers/productController');
const modelMiddleware = require('../middlewares/modelMiddleware');
const modelController = require('../controllers/modelController');
const categoryController = require('../controllers/categoryController');
const cors = require('cors');

router.use(cors({
  origin: '*',
  method: ["GET","POST","DELETE"]
}));

router.post(
  '/register',
  [
    check('email', 'Email is required').not().isEmpty(),
    check('username', 'Username is required').not().isEmpty(),
    check('password', 'Password must be at least 6 characters').isLength({ min: 6 }),
  ],
  (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    authController.register(req, res, next);
  }
);

router.post(
  '/login',
  [
    check('email', 'Email is required').not().isEmpty(),
    check('password', 'Password must be at least 6 characters').isLength({ min: 6 }),
  ],
  (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    authController.login(req, res, next);
  }
);

router.post('/logout', authController.logout);

router.post(
  '/upload-profile-picture',
  authMiddleware.authMiddleware,
  uploadMiddleware,
  uploadController.uploadProfilePicture
);

router.delete(
  '/delete-profile-picture',
  authMiddleware.authMiddleware,
  deleteController.deleteProfilePicture
);

router.get(
  '/listproduct',
  authMiddleware.authMiddleware,
  productController.getProduct
);

router.get(
  '/findproduct',
  authMiddleware.authMiddleware,
  productController.searchProduct
);

router.post(
  '/addfavorite',
  authMiddleware.authMiddleware,
  productController.addFavorit
);

router.delete(
  '/removefavorite',
  authMiddleware.authMiddleware,
  productController.removeFavorite
);

router.get(
  '/listfavorite',
  authMiddleware.authMiddleware,
  productController.listFavorit
);

router.post(
  '/predict', 
  modelMiddleware,
  authMiddleware.authMiddleware,
  modelController.postPredictHandler
);

router.get(
  '/tutorial',
  authMiddleware.authMiddleware,
  modelController.listTutorial
);

router.get(
  '/recommendation',
  authMiddleware.authMiddleware,
  modelController.productRecommendation
);

router.get(
  '/category',
  authMiddleware.authMiddleware,
  categoryController.getCategory
);

router.get(
  '/categorytutorial',
  authMiddleware.authMiddleware,
  categoryController.getCategoryTutorial
);

module.exports = router;
