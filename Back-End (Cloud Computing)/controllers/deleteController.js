const { Storage } = require('@google-cloud/storage');
const path = require('path');
const db = require('../config/db');
require('dotenv').config();

const storage = new Storage({
    keyFilename: path.join(__dirname, '../credentials.json'),
});

const bucketName = process.env.BUCKET_NAME;

exports.deleteProfilePicture = async (req, res) => {
    try {
        const userId = req.user.id;

        const [rows] = await db.execute('SELECT profilePic FROM users WHERE id = ?', [userId]);
        if (rows.length === 0) {
            return res.status(404).send({ error: true, message: 'User not found' });
        }

        const photoUrl = rows[0].profilePic;
        if (!photoUrl) {
            return res.status(400).send({ error: true, message: 'No profile picture to delete' });
        }

        const urlParts = photoUrl.split('/');
        const folderId = urlParts[urlParts.length - 2];
        const fileName = urlParts[urlParts.length - 1];

        const bucket = storage.bucket(bucketName);
        const file = bucket.file(`profilePic/${folderId}/${fileName}`);
        await file.delete();

        await db.execute('UPDATE users SET profilePic = NULL WHERE id = ?', [userId]);

        const [files] = await bucket.getFiles({ prefix: `profilePic/${folderId}/` });
        if (files.length === 0) {
            await bucket.deleteFiles({ prefix: `profilePic/${folderId}` });
        }

        res.status(200).send({ error: false, message: 'Profile picture deleted successfully' });
    } catch (err) {
        console.error('Error during profile picture deletion:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};
