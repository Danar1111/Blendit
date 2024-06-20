const db = require('../config/db');
const { validationResult } = require('express-validator');

exports.getProduct = async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }

    const { page } = req.query;

    const pageNumber = page ? parseInt(page, 10) : 1;
    const pageSize = 20;
    const offset = (pageNumber - 1) * pageSize;

    try {
        const [totalCountResult] = await db.execute('SELECT COUNT(*) as total FROM products');
        const totalItems = totalCountResult[0].total;
    
        const [items] = await db.execute('SELECT * FROM products LIMIT ? OFFSET ?', [pageSize, offset]);
    
        const cleanedItems = items.map(item => ({
            ...item,
            picture: item.picture.trim()
        }));

        const response = {
            error: false,
            message: 'Products items fetched successfully',
            totalItems: totalItems,
            totalPages: Math.ceil(totalItems / pageSize),
            currentPage: pageNumber,
            pageSize: pageSize,
            items: cleanedItems,
        };
    
        // Send the response
        res.status(200).json(response);
    } catch (err) {
        console.error('Error fetching product items:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};

exports.searchProduct = async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }

    const { page, find } = req.query

    const pageNumber = page ? parseInt(page, 10) : 1;
    const pageSize = 20;
    const offset = (pageNumber - 1) * pageSize;

    const searchQuery = `%${find}%`;

    try {
        const [totalCountResult] = await db.execute(
            'SELECT COUNT(*) as total FROM products WHERE product_name LIKE ? OR brand LIKE ?',
            [searchQuery, searchQuery]
        );
        const totalItems = totalCountResult[0].total;
    
        const [items] = await db.execute(
            'SELECT * FROM products WHERE product_name LIKE ? OR brand LIKE ? LIMIT ? OFFSET ?',
            [searchQuery, searchQuery, pageSize, offset]
        );

        const cleanedItems = items.map(item => ({
            ...item,
            picture: item.picture.trim()
        }));
    
        const response = {
            error: false,
            message: 'Products items fetched successfully',
            totalItems: totalItems,
            totalPages: Math.ceil(totalItems / pageSize),
            currentPage: pageNumber,
            pageSize: pageSize,
            items: cleanedItems,
        };
    
        // Send the response
        res.status(200).json(response);
    } catch (err) {
        console.error('Error fetching product items:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};

exports.addFavorit = async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }

    const { userId, productId } = req.body;

    try {
        await db.execute('INSERT INTO favorites (user_id, product_id) VALUES (?, ?)', [userId, productId]);

        res.status(200).send({ error: false , message: 'Successfully added to favorites' });
    } catch (err) {
        console.error('Error fetching product items:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};

exports.removeFavorite = async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }

    const { userId, productId } = req.query;

    try {
        await db.execute('DELETE FROM favorites WHERE user_id = ? AND product_id = ?', [userId, productId]);

        res.status(200).send({ error: false , message: 'Successfully deleted product from favorites' });
    } catch (err) {
        console.error('Error removing product from favorites:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};


exports.listFavorit = async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }

    const { userId } = req.query;

    try {
        const [totalCountResult] = await db.execute(
            `SELECT COUNT(*) as total
            FROM products p 
            INNER JOIN favorites f ON p.id = f.product_id 
            WHERE f.user_id = ?;`,
            [userId]
        );

        const totalItems = totalCountResult[0].total;

        const [items] = await db.execute(
            `SELECT p.id AS product_id, 
                    p.brand, 
                    p.product_name, 
                    p.shade, 
                    p.type, 
                    p.undertone, 
                    p.skintone, 
                    p.makeup_type, 
                    p.skin_type, 
                    p.picture 
            FROM products p 
            INNER JOIN favorites f ON p.id = f.product_id 
            WHERE f.user_id = ?;`,
            [userId]
        );

        const cleanedItems = items.map(item => ({
            ...item,
            picture: item.picture.trim()
        }));

        res.status(200).send({ error: false, 
            message: 'Successfully fetched favorite products', 
            userId: userId, 
            totalItems: totalItems, 
            items: cleanedItems
        });
    } catch (err) {
        console.error('Error fetching favorite products:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};
