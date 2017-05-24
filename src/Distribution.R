# Create a sequence of numbers between 1 and 300 incrementing by 1.
	x <- seq(1, 300, by = 1)
	# Choose the mean as 2.5 and standard deviation as 0.5.
	y <- dnorm(x, mean = 117.19343494725724, sd = 9.469109611318789)
	print(y)
	write.csv(y,"Result\\TempR\\1_output.csv", row.names = FALSE)