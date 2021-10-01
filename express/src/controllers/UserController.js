import express from 'express';
import * as AuthService from '../services/UserService';

const router = express.Router();

router.post('/signup', AuthService.signup);
router.post('/login', AuthService.signup);

export default router;
