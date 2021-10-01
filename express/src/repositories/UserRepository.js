import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

// export const findByEmail = async (email) => {
//     try {
//         return await prisma.user.findUnique({
//             where: { email },
//         });
//     } catch (err) {
//         console.error(err);
//     }
// };

// export const findByNickname = async (nickname) => {
//     try {
//         console.log(nickname);
//         return await prisma.user.findUnique({
//             where: { nickname },
//         });
//     } catch (err) {
//         console.error(err);
//     }
// };

export const findUnique = async (unique) => {
    try {
        return await prisma.user.findUnique({
            where: unique,
        });
    } catch (err) {
        console.error(err);
    }
};

export const create = async (data) => {
    try {
        return await prisma.user.create({
            data: data,
        });
    } catch (err) {
        console.error(err);
    }
};
