import express from 'express';
import * as AuthService from '../services/UserService';

const router = express.Router();

router.post('/', AuthService.signUp);
router.post('/login', AuthService.login);
router.get('/logout', AuthService.logout);

export default router;
