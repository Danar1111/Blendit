const db = require('../config/db');
const { validationResult } = require('express-validator');

exports.getCategory = async (req, res) => {
    try {
        const [totalCountResult] = await db.execute('SELECT COUNT(*) as total FROM category');
        const totalItems = totalCountResult[0].total;
    
        const [items] = await db.execute('SELECT id, category, thumbnail FROM category');
    
        const cleanedItems = items.map(item => ({
            ...item,
            thumbnail: item.thumbnail.trim()
        }));

        const response = {
            error: false,
            message: 'Category fetched successfully',
            totalItems: totalItems,
            items: cleanedItems
        };
    
        res.status(200).json(response);
    } catch (err) {
        console.error('Error category:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};

exports.getCategoryTutorial = async (req, res) => {
    try {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }

        const { id } = req.query;

        const [items] = await db.execute('SELECT * FROM category WHERE id = ?',[id]);

        const response = {
            error: false,
            message: 'Category fetched successfully',
            items: {
                id: items[0].id,
                category: items[0].category,
                skinPreparation: items[0].skinPreparation.replace(/\r/g, ''),
                baseMakeup: items[0].baseMakeup.replace(/\r/g, ''),
                eyeMakeup: items[0].eyeMakeup.replace(/\r/g, ''),
                lipsMakeup: items[0].lipsMakeup.replace(/\r/g, ''),
                skinPrepPic: items[0].skinPrepPic.replace(/\r/g, ''),
                baseMakeupPic: items[0].baseMakeupPic.replace(/\r/g, ''),
                eyeMakeupPic: items[0].eyeMakeupPic.replace(/\r/g, ''),
                lipsMakeupPic: items[0].lipsMakeupPic.replace(/\r/g, ''),
                thumbnail: items[0].thumbnail.replace(/\r/g, '')
            }
        };
    
        res.status(200).json(response);
    } catch (err) {
        console.error('Error category items:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};