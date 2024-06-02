import { PrismaClient } from "@prisma/client";
import express from 'express';
import { connect } from "http2";

const prisma = new PrismaClient();
const app = express();

app.use(express.json()); // parse incoming json requests

// HOME - default endpoint
app.get('/', (req, res) => {
    res.send('Express sever on port 3000!')
})

// CREATE - new user signup information
app.post('/signup', async (req, res) => {
    const { email } = req.body;
    try {
        const data = await prisma.user.create({
            data: {
                email: email,
            }
        });
        res.status(200).json(data);
        console.log(data);
    } catch (error) {
        console.log(error);
        res.status(400).json("Internal server error!");
    }
})

// CREATE - new user post
app.post('/post', async (req, res) => {
    const { title, description, userEmail, isActive, isPrivate, location, createdAt} = req.body;
    try {
        const data = await prisma.post.create({
            data: {
                title: title,
                description: description,
                user: {
                    connect: { email: userEmail }
                },
                isActive: isActive,
                isPrivate: isPrivate,
                location: location,
                createdAt: createdAt
            }
        });
        res.json(data);
        console.log(data);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Internal server error' });
        console.log(data);
    }
});

// GET ALL POSTS
app.get('/posts', async (req, res) => {
    const data = await prisma.post.findMany();
    res.json(data);
})





app.listen(3000, () => {
    console.log(`Server is running on port ${3000}`)
})