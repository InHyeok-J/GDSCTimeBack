const catchAsync = (fn) => (req, res, next) => {
    Promise.resolve(fn(req, res, next))
        .then((value) => {
            const { status, message, data } = value;
            let success = true;
            if (status >= 400) {
                success = false;
            }

            return res.status(value.status).send({
                status,
                message,
                success,
                data,
            });
        })
        .catch((err) => {
            console.error(err);
            next(err);
        });
};

export default catchAsync;
