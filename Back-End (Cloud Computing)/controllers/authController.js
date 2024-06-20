const db = require('../config/db');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const { validationResult } = require('express-validator');
const crypto = require('crypto');

const generateUniqueId = () => {
  return 'user-' + crypto.randomBytes(8).toString('base64').replace(/\//g, '_').replace(/\+/g, '-');
};

exports.register = async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() });
  }

  const { email, username, password } = req.body;

  try {
    const [user] = await db.execute('SELECT * FROM users WHERE email = ?', [email]);
    if (user.length > 0) {
      return res.status(400).json({ error: true, message: 'Email already exists' });
    }

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    const userId = generateUniqueId();

    await db.execute('INSERT INTO users (id, email, username, password, profilePic) VALUES (?, ?, ?, ?, ?)', [userId, email, username, hashedPassword, null]);

    res.status(201).send({ error: false , message: 'User registered' });

  } catch (err) {
    console.error('Error during user registration:', err);
    res.status(500).send({ error: true ,message: 'Server error' });
  }
};

exports.login = async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() });
  }

  const { email, password } = req.body;

  try {
    const [user] = await db.execute('SELECT * FROM users WHERE email = ?', [email]);
    if (user.length === 0) {
      return res.status(400).json({ error: true, message: 'Invalid credentials' });
    }

    const validPassword = await bcrypt.compare(password, user[0].password);
    if (!validPassword) {
      return res.status(400).json({ error: true, message: 'Invalid credentials' });
    }

    const token = jwt.sign({ id: user[0].id }, process.env.JWT_SECRET);
    res.header('Authorization', token).send({ 
      error: false,
      message: 'Login successful',
      loginResult: {userId: user[0].id,
        email: email,
        username: user[0].username,
        token,
        profilePic: user[0].profilePic
      } 
    });

  } catch (err) {
    console.error('Error during user login:', err);
    res.status(500).send({ error: true, message: 'Server error' });
  }
};

exports.logout = (req, res) => {
  //nunggu MD, implementasi hapus token di client-side
  res.status(200).send({ error: false, message: 'Logout successful' });
};
