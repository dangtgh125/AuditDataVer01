# Create a sequence of numbers between 1 and 25 incrementing by 1.
	x <- seq(1, 25, by = 1)
	# Choose the mean as 2.5 and standard deviation as 0.5.
	y <- dnorm(x, mean = 5.327605901967441, sd = 0.9442397095348336)
	print(y)
	write.csv(y,"Result\\TempR\\2_output.csv", row.names = FALSE)