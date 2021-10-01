const catchAsync = (fn) => (req, res, next) => {
    Promise.resolve(fn(req, res, next)).catch((err) => {
        console.log('에러 영역');
        console.error(err);
        next(err);
    });
};

export default catchAsync;
