# Create a sequence of numbers between 1 and 40 incrementing by 1.
	x <- seq(1, 40, by = 1)
	# Choose the mean as 2.5 and standard deviation as 0.5.
	y <- dnorm(x, mean = 13.316219310276614, sd = 3.303016347206099)
	print(y)
	write.csv(y,"Result\\TempR\\2_output.csv", row.names = FALSE)