import * as UserRepository from '../repositories/UserRepository';
import bcrypt from 'bcrypt';
import catchAsync from '../utils/catchAsync';
import returnType from '../utils/returnType';

export const signup = catchAsync(async (req, res) => {
    const existUserEmail = await UserRepository.findByEmail(req.body.email);
    if (existUserEmail) {
        return returnType(404, '이메일 존재');
    }

    const existUserNicknmae = await UserRepository.findByNickname(
        req.body.nickname
    );

    if (existUserNicknmae) {
        return returnType(404, '닉네임 존재');
    }

    req.body.password = await bcrypt.hash(req.body.password, 10);

    const user = await UserRepository.create(req.body);
    if (!user) {
        return returnType(500, '알수 없는 에러');
    }
    delete user['password'];
    return returnType(200, '이메일 존재', user);
});

export const login = catchAsync(async (req, res) => {});
